package fr.uge.poo.newsletter.question4;

@FunctionalInterface
public interface UserCondition {

  boolean test(User user);
}
