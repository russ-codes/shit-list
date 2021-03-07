/* Licensed under Apache-2.0 */
package io.shit.list.services;

import io.shit.list.domain.Repository;
import io.shit.list.repositories.RepositoryRepository;
import io.shit.list.utils.ContentUtils;
import java.io.File;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService {

  private final RepositoryRepository repositoryRepository;

  private final MessageService messageService;

  private final GitService gitService;

  private final FileService fileService;

  private final TotalService totalService;

  private final Scheduler scheduler = Schedulers.newBoundedElastic(8, 16, "non-blocking");

  @Override
  public Mono<Repository> save(final Repository repository) {
    log.info("Saving: {}", repository);

    return Mono.justOrEmpty(repositoryRepository.save(repository));
  }

  @Override
  public Flux<Repository> findAll() {

    return Flux.fromIterable(repositoryRepository.findAll());
  }

  @Override
  public Mono<Repository> findById(final long id) {

    return Mono.justOrEmpty(repositoryRepository.findById(id));
  }

  @Override
  public Mono<Void> deleteById(final long id) {
    repositoryRepository.deleteById(id);

    return Mono.empty();
  }

  @Override
  public Mono<Repository> sync(Repository repository) {

    return Mono.fromCallable(() -> repositoryRepository.findById(repository.getId()))
        .subscribeOn(this.scheduler)
        .flatMap(optional -> optional.map(Mono::just).orElseGet(Mono::empty))
        .doOnNext(this::setProcessing)
        .flatMap(this::process)
        .doOnNext(this::setComplete);
  }

  @Override
  public Flux<Repository> sync() {
    totalService.zeroTotal();

    return Flux.fromIterable(repositoryRepository.findAll())
        .doOnNext(this::setProcessing)
        .flatMap(this::process)
        .doOnNext(this::setComplete);
  }

  @Override
  public Mono<Repository> setProcessing(final Repository repository) {
    repository.setState("processing");

    return saveAndNotify(repository);
  }

  @Override
  public Mono<Repository> setComplete(final Repository repository) {
    repository.setState("complete");

    return saveAndNotify(repository);
  }

  private Mono<Repository> saveAndNotify(final Repository repository) {
    this.messageService.sendMessage("/topic/repository", repository);

    return save(repository);
  }

  @SneakyThrows
  private Mono<Repository> process(final Repository repository) {

    return Mono.defer(
            () ->
                Mono.fromFuture(gitService.cloneRepository(repository))
                    .map(repo -> fileService.findAllJavaFiles(Path.of(repo.getDirectory())))
                    .doOnNext(paths -> totalService.increaseTotalTests(filterTests(paths)))
                    .doOnNext(paths -> totalService.increaseTotalIgnored(filterIgnored(paths)))
                    .doAfterTerminate(
                        () -> FileUtils.deleteQuietly(new File(repository.getDirectory()))))
        .thenReturn(repository);
  }

  private long filterTests(final java.util.List<Path> paths) {
    return paths.stream().filter(ContentUtils::hasTestContent).count();
  }

  private long filterIgnored(final java.util.List<Path> paths) {
    return paths.stream()
        .filter(p -> ContentUtils.hasIgnoredContent(p) || ContentUtils.hasDisabledContent(p))
        .count();
  }
}
