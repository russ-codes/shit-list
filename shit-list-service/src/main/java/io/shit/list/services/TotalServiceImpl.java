/* Licensed under Apache-2.0 */
package io.shit.list.services;

import io.shit.list.domain.Total;
import io.shit.list.repositories.TotalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TotalServiceImpl implements TotalService {

  private final TotalRepository totalRepository;

  private final MessageService messageService;

  @Override
  public synchronized Total find() {
    return totalRepository
        .findById(1L)
        .orElse(Total.builder().totalTests(0).totalIgnored(0).build());
  }

  @Override
  public synchronized Total zeroTotal() {
    final Total total = find();

    total.setTotalTests(0);
    total.setTotalIgnored(0);

    return totalRepository.save(total);
  }

  @Override
  public synchronized Total increaseTotalTests(long value) {
    final Total total = find();

    total.setTotalTests(total.getTotalTests() + value);

    return saveAndNotify(total);
  }

  @Override
  public synchronized Total increaseTotalIgnored(long value) {
    final Total total = find();

    total.setTotalIgnored(total.getTotalIgnored() + value);

    return saveAndNotify(total);
  }

  private synchronized Total saveAndNotify(Total total) {
    final Total savedTotal = totalRepository.save(total);

    this.messageService.sendMessage("/topic/total", savedTotal);

    return savedTotal;
  }
}
