/* Licensed under Apache-2.0 */
package io.shit.list.controller;

import io.shit.list.domain.Configuration;
import io.shit.list.services.ConfigurationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/api/configuration")
@RequiredArgsConstructor
public class ConfigurationController {

  private final ConfigurationService configurationService;

  @PostMapping
  public Mono<Configuration> save(@RequestBody final Configuration configuration) {

    return configurationService.save(configuration);
  }

  @GetMapping
  public Mono<Configuration> find() {
    return configurationService.find();
  }
}
