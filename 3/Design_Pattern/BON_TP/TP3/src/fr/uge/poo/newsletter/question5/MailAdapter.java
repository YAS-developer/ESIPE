package fr.uge.poo.newsletter.question5;

import java.util.List;
import java.util.Objects;

public interface MailAdapter {

  default void send(User recipient, String subject, String content) {
    send(recipient.email(), subject, content);
  }

  void send(String recipient, String subject, String content);


  default void bulkSend(List<User> recipients, String subject, String content) {
    Objects.requireNonNull(recipients);
    Objects.requireNonNull(subject);
    Objects.requireNonNull(content);

    for(var user : recipients) {
      send(user, subject, content);
    }
  }

}
