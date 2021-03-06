/* Licensed under Apache-2.0 */
package io.shit.list.controller;

import io.shit.list.domain.Repository;
import io.shit.list.services.RepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/repository")
@RequiredArgsConstructor
public class RepositoryController {

  private final RepositoryService repositoryService;

  @PostMapping
  public Mono<Repository> save(@RequestBody final Repository repository) {

    return repositoryService.save(repository);
  }

  @GetMapping
  public Flux<Repository> findAll() {

    return repositoryService.findAll();
  }

  @GetMapping("/{id}")
  public Mono<Repository> findById(@PathVariable final long id) {

    return repositoryService.findById(id);
  }

  @DeleteMapping("/{id}")
  public Mono<Void> DeleteById(@PathVariable final long id) {

    return repositoryService.deleteById(id);
  }
}
