/* Licensed under Apache-2.0 */
package io.shit.list.services;

import java.io.IOException;
import java.nio.file.Path;
import org.eclipse.jgit.api.errors.GitAPIException;

public interface GitService {

  Path clone(String cloneUrl) throws GitAPIException, IOException;
}
