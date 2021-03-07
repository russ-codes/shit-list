/* Licensed under Apache-2.0 */
package io.shit.list.repositories;

import io.shit.list.domain.Total;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotalRepository
        extends CrudRepository<Total, Long> {
}
