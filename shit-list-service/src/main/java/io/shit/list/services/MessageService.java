/* Licensed under Apache-2.0 */
package io.shit.list.services;

public interface MessageService {

  void sendMessage(String topic, Object payload);
}
