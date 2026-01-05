package fr.uge.poo.newsletter.question1;

import com.evilcorp.eemailer.EEMailer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Newsletter {

  private final String name;

  private final Map<String, User> subscribers = new HashMap<>();

  private final static EEMailer MAILER = new EEMailer();

  public Newsletter(String name) {
    this.name = name;
  }

  public void subscribe(User user) {
    Objects.requireNonNull(user);
    var tmpUser = subscribers.putIfAbsent(user.email(), user);
    if (tmpUser != null) {
      throw new IllegalArgumentException("Already sub");
    }
  }

  public void unsubscribe(String email) {
    Objects.requireNonNull(email);
    subscribers.remove(email);
  }

  public void sendMessage(String title, String content) {
    Objects.requireNonNull(title);
    Objects.requireNonNull(content);
    for (var user : subscribers.values()) {
      var mail = new EEMailer.Mail(user.email(), "[" + name + "] " + title, content);
      MAILER.send(mail);
    }
  }
}
