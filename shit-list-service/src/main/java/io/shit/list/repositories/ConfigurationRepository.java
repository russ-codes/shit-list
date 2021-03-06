/* Licensed under Apache-2.0 */
package io.shit.list.repositories;

import io.shit.list.domain.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends CrudRepository<Configuration, Long> {}
