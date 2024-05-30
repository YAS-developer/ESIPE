package fr.uge.bank;

public record CreditOrder(int amount, int accountId) implements Order {
  
  public CreditOrder {
    if (! Order.checkAmount(amount)) {
      throw new IllegalArgumentException();
    }
  }

}
