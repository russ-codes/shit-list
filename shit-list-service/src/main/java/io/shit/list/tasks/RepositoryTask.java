/* Licensed under Apache-2.0 */
package io.shit.list.tasks;

import io.shit.list.domain.Repository;
import io.shit.list.services.ConfigurationService;
import io.shit.list.services.FileService;
import io.shit.list.services.GitService;
import io.shit.list.services.RepositoryService;
import io.shit.list.utils.ContentUtils;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RepositoryTask {

  private static int totalTests;

  private static int totalIgnored;

  private final RepositoryService repositoryService;

  private final ConfigurationService configurationService;

  private final GitService gitService;

  private final FileService fileService;

  /** For every repository we have, clone it down for processing. */
  public void sync() {
    totalTests = 0;
    totalIgnored = 0;

    repositoryService.findAll().doOnNext(this::process).subscribe();

    log.info("Files with tests {}", totalTests);
    log.info("Files with ignored {}", totalIgnored);

    configurationService.updateStats(totalTests, totalIgnored).subscribe();
  }

  @SneakyThrows
  private void process(final Repository repository) {
    final Path path = gitService.clone(repository.getCloneUrl());

    final List<Path> javaFiles = fileService.findAllJavaFiles(path);

    totalTests = (int) javaFiles.stream().filter(ContentUtils::hasTestContent).count();
    totalIgnored =
        (int)
            javaFiles.stream()
                .filter(
                    p -> ContentUtils.hasIgnoredContent(p) || ContentUtils.hasDisabledContent(p))
                .count();
  }
}
