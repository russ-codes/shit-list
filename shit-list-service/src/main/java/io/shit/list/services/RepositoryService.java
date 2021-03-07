/* Licensed under Apache-2.0 */
package io.shit.list.services;

import io.shit.list.domain.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RepositoryService {

  Mono<Repository> save(Repository repository);

  Flux<Repository> findAll();

  Mono<Repository> findById(long id);

  Mono<Void> deleteById(long id);

  Mono<Repository> sync(Repository repository);

  Flux<Repository> syncAll();

  Mono<Repository> setProcessing(final Repository repository);

  Mono<Repository> setComplete(final Repository repository);
}
