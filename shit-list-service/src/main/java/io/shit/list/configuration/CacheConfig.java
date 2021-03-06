/* Licensed under Apache-2.0 */
package io.shit.list.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfig {

  /** Duration in minutes to cache web results */
  private static final int DURATION = 5;

  @Bean
  public Caffeine<Object, Object> caffeineConfig() {
    return Caffeine.newBuilder().expireAfterWrite(DURATION, TimeUnit.MINUTES);
  }

  @Bean
  public CacheManager cacheManager(final Caffeine<Object, Object> caffeine) {
    final CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
    caffeineCacheManager.setCaffeine(caffeine);

    return caffeineCacheManager;
  }
}
