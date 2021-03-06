/* Licensed under Apache-2.0 */
package io.shit.list.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** God (and or Russ) forgive me for the name of this class. */
@Repository
public interface RepositoryRepository
    extends CrudRepository<io.shit.list.domain.Repository, Long> {}
