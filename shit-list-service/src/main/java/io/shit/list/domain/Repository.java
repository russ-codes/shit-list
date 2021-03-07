/* Licensed under Apache-2.0 */
package io.shit.list.domain;

import java.nio.file.Path;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Repository {

  @Id @GeneratedValue private long id;

  private String cloneUrl;

  @Nullable private String directory;

  private long totalTests;

  private long totalIgnored;

  /** Default state any repository has when first added. */
  private String state = "new";

  /** Returns null if no directory is set, be careful when using this. */
  public Path getDirectory() {
    return StringUtils.isEmpty(this.directory) ? null : Path.of(this.directory);
  }
}
