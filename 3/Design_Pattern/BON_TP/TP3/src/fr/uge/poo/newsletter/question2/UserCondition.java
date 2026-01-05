package fr.uge.poo.newsletter.question2;

@FunctionalInterface
public interface UserCondition {

  boolean test(User user);
}
