/* Licensed under Apache-2.0 */
package io.shit.list.controller;

import io.shit.list.domain.Repository;
import io.shit.list.services.RepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/repository")
@RequiredArgsConstructor
public class RepositoryController {

  private final RepositoryService repositoryService;

  /**
   * When saving a new repository, kick off a scan on a different thread so the UI can return as
   * quick as possible.
   */
  @PostMapping
  public Mono<Repository> save(@RequestBody final Repository repository) {
    return repositoryService
        .save(repository)
        .publishOn(Schedulers.boundedElastic())
        .doOnSuccess((r) -> repositoryService.sync(r).subscribe());
  }

  @GetMapping
  public Flux<Repository> findAll() {

    return repositoryService.findAll();
  }

  @GetMapping("/{id}")
  public Mono<Repository> findById(@PathVariable final long id) {

    return repositoryService.findById(id);
  }

  /**
   * When deleting a repository, we recalculate all projects. This is a huge waste of resources.
   * Ideally we will save how many tests/ignores each project has then we can just subtract those
   * values but that seems like a bit of work...
   */
  @DeleteMapping("/{id}")
  public Mono<Void> deleteById(@PathVariable final long id) {

    return repositoryService
        .deleteById(id)
        .publishOn(Schedulers.boundedElastic())
        .doOnSuccess((ignored) -> repositoryService.syncAll().subscribe());
  }
}
