/* Licensed under Apache-2.0 */
package io.shit.list.controller;

import io.shit.list.domain.Repository;
import io.shit.list.services.RepositoryService;
import io.shit.list.tasks.RepositoryTask;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final RepositoryTask repositoryTask;

    @GetMapping("/sync")
    public Flux<Repository> sync() {

        return repositoryTask.sync();
    }
}
