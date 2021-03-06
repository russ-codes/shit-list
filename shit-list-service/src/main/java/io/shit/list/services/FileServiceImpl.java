/* Licensed under Apache-2.0 */
package io.shit.list.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

  @Override
  public List<Path> findAllJavaFiles(final Path source) {
    try {
      return Files.walk(source)
          .filter(Files::isReadable)
          .filter(this::isNotDirectory)
          .filter(this::isJavaFile)
          .collect(Collectors.toCollection(ArrayList::new));
    } catch (final IOException ioException) {
      log.warn("Unable to scan for Java files {}", ioException.getMessage(), ioException);

      return Collections.emptyList();
    }
  }

  private boolean isJavaFile(final Path path) {
    return FilenameUtils.getExtension(path.getFileName().toString()).contains("java");
  }

  private boolean isNotDirectory(final Path path) {
    return !Files.isDirectory(path);
  }
}
