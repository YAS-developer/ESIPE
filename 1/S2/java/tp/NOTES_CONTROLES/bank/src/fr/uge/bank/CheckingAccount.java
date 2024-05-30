package fr.uge.bank;

import java.util.Objects;

public record CheckingAccount(int accountId, String name, int dateOfBirth) implements Account{

  public CheckingAccount {
    Objects.requireNonNull(name);
    if (dateOfBirth < 0 || dateOfBirth > MAX_DATE_OF_BIRTH) {
      throw new IllegalArgumentException();
    }
  }
  
  @Override
  public String toString() {
    return "Checking account " + accountId + ": " + name + " (" + dateOfBirth  + ")";
  }
}
