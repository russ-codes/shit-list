/* Licensed under Apache-2.0 */
package io.shit.list.services;

import java.nio.file.Path;
import java.util.List;

public interface FileService {

  List<Path> findAllJavaFiles(Path source);
}
