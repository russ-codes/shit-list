/* Licensed under Apache-2.0 */
package io.shit.list.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Total {

  @Id @JsonIgnore private final long id = 1L;

  private long totalTests;

  private long totalIgnored;
}
