package fr.uge.bank;

public sealed interface Order permits CreditOrder, DebitATMOrder{
  
  static boolean checkAmount(int amount) {
    return amount >= 0;
  }
  
  int accountId();
  int amount();
}
