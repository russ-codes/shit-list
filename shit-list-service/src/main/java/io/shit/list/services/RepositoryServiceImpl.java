/* Licensed under Apache-2.0 */
package io.shit.list.services;

import io.shit.list.domain.Repository;
import io.shit.list.repositories.RepositoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService {

  private final RepositoryRepository repositoryRepository;

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
}
