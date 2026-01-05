package fr.uge.poo.newsletter.question4;

import com.evilcorp.eemailer.EEMailer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class Newsletter {

  public static class Builder {

    private String name;

    private final Map<String, User> subscribers = new HashMap<>();

    private Predicate<User> userPredicate = u -> true;

    private MailAdapter mailAdapter = new GMailAdapter();

    private Builder() {

    }

    public Builder name(String name) {
      Objects.requireNonNull(name);
      this.name = name;
      return this;
    }

    public Builder subscribe(User user) {
      Objects.requireNonNull(user);

      var tmpUser = subscribers.putIfAbsent(user.email(), user);
      if (tmpUser != null) {
        throw new IllegalArgumentException("Already sub");
      }

      return this;
    }

    public Builder subscribe(List<User> users) {
      Objects.requireNonNull(users);
      for (var user : users) {
        subscribe(user);
      }
      return this;
    }

    public Builder minAge(int age) {
      var tmpPredicate = userPredicate;
      userPredicate = u -> u.age() >= age && tmpPredicate.test(u);
      return this;
    }

    public Builder maxAge(int age) {
      var tmpPredicate = userPredicate;
      userPredicate = u -> u.age() <= age && tmpPredicate.test(u);
      return this;
    }

    public Builder allowedNationality(User.Nationality... nationality) {
      var tmpUserPredicate = userPredicate;
      userPredicate = u -> List.of(nationality).contains(u.nationality()) && tmpUserPredicate.test(u);
      return this;
    }

    public Builder condition(Predicate<User> condition) {
      var tmpPredicate = userPredicate;
      userPredicate = u -> condition.test(u) && tmpPredicate.test(u);
      return this;
    }

    public Builder mailSystem(MailAdapter adapter) {
      Objects.requireNonNull(adapter);
      mailAdapter = adapter;
      return this;
    }

    public Newsletter build() {
      Objects.requireNonNull(name);

      for(var user : subscribers.values()) {
        if (!userPredicate.test(user)) {
          throw new IllegalArgumentException("Condition failed");
        }
      }

      return new Newsletter(this);
    }
  }

  public static Builder with() {
    return new Builder();
  }

  private final String name;

  private final Map<String, User> subscribers = new HashMap<>();

  private final Predicate<User> userCondition;

  private MailAdapter mailAdapter = new GMailAdapter();

  public Newsletter(Builder builder) {
    name = builder.name;
    userCondition = builder.userPredicate;
    subscribers.putAll(builder.subscribers);
    mailAdapter = builder.mailAdapter;
  }

  public void subscribe(User user) {
    Objects.requireNonNull(user);

    if (!userCondition.test(user)) {
      throw new IllegalArgumentException("Condition failed");
    }

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
    mailAdapter.bulkSend(List.copyOf(subscribers.values()), "[" + name + "] " + title, content);
  }
}
