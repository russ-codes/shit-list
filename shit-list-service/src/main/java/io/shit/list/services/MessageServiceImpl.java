/* Licensed under Apache-2.0 */
package io.shit.list.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

  private final SimpMessagingTemplate messagingTemplate;

  @Override
  public void sendMessage(final String topic, final Object payload) {

    log.info("Sending message to {}", topic);

    messagingTemplate.convertAndSend(topic, payload);
  }
}
