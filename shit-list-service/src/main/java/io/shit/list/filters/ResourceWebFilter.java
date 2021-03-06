/* Licensed under Apache-2.0 */
package io.shit.list.filters; /* Licensed under Apache-2.0 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ResourceWebFilter implements WebFilter {

  @Override
  public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
    return exchange.getRequest().getURI().getPath().equals("/")
        ? chain.filter(
            exchange
                .mutate()
                .request(exchange.getRequest().mutate().path("/index.html").build())
                .build())
        : chain.filter(exchange);
  }
}
