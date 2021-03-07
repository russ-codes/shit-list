/* Licensed under Apache-2.0 */
package io.shit.list.services;

import io.shit.list.domain.Configuration;
import reactor.core.publisher.Mono;

public interface ConfigurationService {

  Mono<Configuration> save(Configuration configuration);

  Mono<Configuration> find();
}
