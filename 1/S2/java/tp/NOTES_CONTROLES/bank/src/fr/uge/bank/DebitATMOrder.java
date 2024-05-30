package fr.uge.bank;

public record DebitATMOrder(int amount, int accountId, int visaId) implements Order {
  
  public DebitATMOrder {
    if (! Order.checkAmount(amount)) {
      throw new IllegalArgumentException();
    }
  }
}
