package fr.uge.poo.newsletter.question3;

@FunctionalInterface
public interface UserCondition {

  boolean test(User user);
}
