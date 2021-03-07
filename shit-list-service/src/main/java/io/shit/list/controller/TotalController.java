/* Licensed under Apache-2.0 */
package io.shit.list.controller;

import io.shit.list.domain.Total;
import io.shit.list.services.TotalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/total")
@RequiredArgsConstructor
public class TotalController {

  private final TotalService totalService;

  @GetMapping
  public Mono<Total> findTotal() {

    return Mono.just(totalService.find());
  }
}
