/* Licensed under Apache-2.0 */
package io.shit.list.services;

import io.shit.list.domain.Configuration;
import io.shit.list.repositories.ConfigurationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfigurationServiceImpl implements ConfigurationService {

  private final ConfigurationRepository configurationRepository;

  @Override
  public Mono<Configuration> save(final Configuration configuration) {

    log.info("Saving: {}", configuration);

    return Mono.justOrEmpty(configurationRepository.save(configuration));
  }

  @Override
  public Mono<Configuration> find() {

    final Configuration configuration =
        configurationRepository.findById(1L).orElse(Configuration.builder().build());

    return Mono.justOrEmpty(configuration);
  }

  @Override
  public Mono<Configuration> updateStats(final long totalTests, final long totalIgnored) {
    return Mono.empty();
  }
}
