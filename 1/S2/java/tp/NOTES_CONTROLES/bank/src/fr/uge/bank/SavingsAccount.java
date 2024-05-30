package fr.uge.bank;

import java.util.Objects;


public record SavingsAccount(int accountId, String name, int dateOfBirth) implements Account {

  public SavingsAccount {
    Objects.requireNonNull(name);
    if (dateOfBirth < 0 || dateOfBirth > MAX_DATE_OF_BIRTH) {
      throw new IllegalArgumentException();
    }
  }
    
  @Override
  public String toString() {
    return "Savings account " + accountId + ": " + name + " (" + dateOfBirth  + ")";
  }
}
