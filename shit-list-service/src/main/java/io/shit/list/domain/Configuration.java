/* Licensed under Apache-2.0 */
package io.shit.list.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Configuration {

  @Id @JsonIgnore private final long id = 1L;

  private String username;

  private String accessToken;

  private long totalTests;

  private long totalIgnored;
}
