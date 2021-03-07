/* Licensed under Apache-2.0 */
package io.shit.list.services;

import io.shit.list.domain.Repository;
import java.util.concurrent.CompletableFuture;

public interface GitService {

  CompletableFuture<Repository> cloneRepository(Repository repository);
}
