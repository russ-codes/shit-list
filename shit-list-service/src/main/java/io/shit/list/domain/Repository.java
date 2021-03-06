/* Licensed under Apache-2.0 */
package io.shit.list.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
public class Repository {

  @GeneratedValue @Id private long id;

  private String cloneUrl;
}
