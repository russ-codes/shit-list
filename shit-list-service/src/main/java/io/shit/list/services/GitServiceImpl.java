/* Licensed under Apache-2.0 */
package io.shit.list.services;

import io.shit.list.domain.Configuration;
import io.shit.list.domain.Repository;
import io.shit.list.repositories.ConfigurationRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitServiceImpl implements GitService {

  private final ConfigurationRepository configurationRepository;

  /**
   * Clone a repository into a temporary directory. This is a bit dirty but works the blocking clone
   * process into a future so we can consume it as a Mono down stream.
   */
  @Override
  public CompletableFuture<Repository> cloneRepository(final Repository repository) {
    if (Objects.isNull(repository.getDirectory())) {
      repository.setDirectory(createWorkingDirectory());
    }

    return CompletableFuture.supplyAsync(
        () -> {
          executeClone(repository);

          return repository;
        });
  }

  /** Create a clone command with credentials if required. */
  private CloneCommand getCloneCommand() {
    final CloneCommand cloneCommand = Git.cloneRepository();
    credentialsProvider().ifPresent(cloneCommand::setCredentialsProvider);

    return cloneCommand;
  }

  /** Preform the actual cloning of the repository. */
  private void executeClone(final Repository repository) {
    try {
      getCloneCommand()
          .setURI(repository.getCloneUrl())
          .setDirectory(repository.getDirectory().toFile())
          .call();
    } catch (GitAPIException gitAPIException) {
      log.error("Unable to clone repository {}", gitAPIException.getMessage(), gitAPIException);
    }
  }

  /**
   * Attempt to create a generic working directory to clone stuff into. This will go into the system
   * default temp directory.
   */
  private String createWorkingDirectory() {
    try {
      return Files.createTempDirectory("shit.list.").toString();
    } catch (IOException ioException) {
      log.error("Unable to create working directory {}", ioException.getMessage(), ioException);
      throw new RuntimeException(ioException);
    }
  }

  /**
   * Grab the configuration properties from the database and if possible, load them into a
   * Credential provider for Git.
   */
  private Optional<UsernamePasswordCredentialsProvider> credentialsProvider() {

    return configurationRepository
        .findById(1L)
        .filter(filterUnsafeCredentials())
        .map(
            value ->
                new UsernamePasswordCredentialsProvider(
                    value.getUsername(), value.getAccessToken()));
  }

  /**
   * Don't attempt to use credentials that have either username or access token as blank. JGit
   * doesn't like it.
   */
  private Predicate<Configuration> filterUnsafeCredentials() {

    return configuration ->
        StringUtils.isNoneEmpty(configuration.getAccessToken())
            && StringUtils.isNoneEmpty(configuration.getAccessToken());
  }
}
