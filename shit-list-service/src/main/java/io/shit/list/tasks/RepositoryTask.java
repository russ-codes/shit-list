/* Licensed under Apache-2.0 */
package io.shit.list.tasks;

import io.shit.list.domain.Repository;
import io.shit.list.services.*;
import io.shit.list.utils.ContentUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RepositoryTask {

    private final RepositoryService repositoryService;

    private final GitService gitService;

    private final FileService fileService;

    private final TotalService totalService;

    private final MessageService messageService;

    /**
     * For every repository we have, clone it down for processing.
     * @return
     */
    public Flux<Repository> sync() {
        totalService.zeroTotal();

        return repositoryService.findAll().doOnNext(this::process).flatMap(repositoryService::save);
    }

    @SneakyThrows
    private Mono<Repository> process(final Repository repository) {
        repository.setState("Processing");
        this.messageService.sendMessage("/topic/repository", repository);

        final Path path = gitService.clone(repository.getCloneUrl());
        final List<Path> javaFiles = fileService.findAllJavaFiles(path);

        totalService.increaseTotalTests(javaFiles.stream().filter(ContentUtils::hasTestContent).count());
        totalService.increaseTotalIgnored(
                javaFiles.stream()
                        .filter(p -> ContentUtils.hasIgnoredContent(p) || ContentUtils.hasDisabledContent(p))
                        .count());

        repository.setState("Complete");
        this.messageService.sendMessage("/topic/repository", repository);

        return Mono.just(repository);
    }
}
