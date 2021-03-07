/* Licensed under Apache-2.0 */
package io.shit.list.services;

import io.shit.list.domain.Total;

public interface TotalService {

  Total find();

  Total zeroTotal();

  Total increaseTest(long value);

  Total increaseIgnored(long value);
}
