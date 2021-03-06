/* Licensed under Apache-2.0 */
package io.shit.list.services;

import io.shit.list.domain.Configuration;
import io.shit.list.repositories.ConfigurationRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitServiceImpl implements GitService {

  private final ConfigurationRepository configurationRepository;

  /**
   * Clone a repository into a temporary directory and return that directory. If unable to clone or
   * create the working directory, exceptions are hidden for now because I'm lazy...
   */
  @Override
  @SneakyThrows
  public Path clone(final String cloneUrl) {

    final Path workingDirectory = createWorkingDirectory();
    final CloneCommand cloneCommand = Git.cloneRepository();

    // Only set credentials if we have them
    credentialsProvider().ifPresent(cloneCommand::setCredentialsProvider);

    cloneCommand.setURI(cloneUrl).setDirectory(workingDirectory.toFile()).call();

    log.info("Cloned {} into {}", cloneUrl, workingDirectory);

    return workingDirectory;
  }

  /**
   * Attempt to create a generic working directory to clone stuff into. This will go into the system
   * default temp directory.
   */
  private Path createWorkingDirectory() throws IOException {
    final Path tempDirectory = Files.createTempDirectory("shit.list.");

    log.info("Working directory: {}", tempDirectory.toFile().toString());

    return tempDirectory;
  }

  /**
   * Grab the configuration properties from the database and if possible, load them into a
   * Credential provider for Git.
   */
  private Optional<UsernamePasswordCredentialsProvider> credentialsProvider() {

    final Optional<Configuration> configuration = configurationRepository.findById(1L);

    return configuration.map(
        value ->
            new UsernamePasswordCredentialsProvider(value.getUsername(), value.getAccessToken()));
  }
}
