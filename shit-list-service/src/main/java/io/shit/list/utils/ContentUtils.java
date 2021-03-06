/* Licensed under Apache-2.0 */
package io.shit.list.utils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

@Slf4j
@UtilityClass
public class ContentUtils {

  public boolean hasTestContent(final Path path) {
    return hasContent("@Test", path);
  }

  /** Junit 4 uses Ignored */
  public boolean hasIgnoredContent(final Path path) {
    return hasContent("@Ignored", path);
  }

  /** Junit 5 uses Disabled */
  public boolean hasDisabledContent(final Path path) {
    return hasContent("@Disabled", path);
  }

  @SneakyThrows
  private boolean hasContent(final String content, final Path path) {
    return FileUtils.readFileToString(path.toFile(), StandardCharsets.UTF_8).contains(content);
  }
}
