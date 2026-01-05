package fr.uge.poo.newsletter.question5;

@FunctionalInterface
public interface UserCondition {

  boolean test(User user);
}
