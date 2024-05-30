package fr.uge.bank;

public class Main {
  
  public static void main(String[] args) {
    var bank = new Bank();
    var account101 = new CheckingAccount(101, "Joe First", 1999);
    var account102 = new CheckingAccount(102, "Jane Second", 2001);
    var account007 = new SavingsAccount(7, "James Bond", 1960);
    var account008 = new CheckingAccount(8, "James Bond", 1960); 
    bank.addAccount(account101);
    bank.addAccount(account102);
    bank.addAccount(account007);
    bank.addAccount(account008);
    System.out.println(bank);
    // Checking account 101: Joe First (1999) balance: 0
    // Checking account 102: Jane Second (2001) balance: 0
    // Savings account 7: James Bond (1960) balance: 0
    // Checking account 8: James Bond (1960) balance: 0
    var accountList = bank.findBornAfter(2000);
    System.out.println(accountList);
    // [Checking account 102: Jane Second (2001)]
    var accountList2 = bank.findCheckingAccounts();
    System.out.println(accountList2);
    // [Checking account 101: Joe First (1999), 
    // Checking account 102: Jane Second (2001), 
    // Checking account 8: James Bond (1960)]
    var map = bank.accountsByName();
    System.out.println(map);
    //{James Bond=[Savings account 7: James Bond (1960), 
    // Checking account 8: James Bond (1960)], 
    // Jane Second=[Checking account 102: Jane Second (2001)], 
    // Joe First=[Checking account 101: Joe First (1999)]}
    var order1 = new CreditOrder(1_000, 101);
    var order2 = new DebitATMOrder(500, 7, 567_583);
    bank.changeBalance(1_300, 101);
    bank.changeBalance(30, 7);
    bank.changeBalance(700, 101);
    System.out.println(bank.balance(7));    // 30
    System.out.println(bank.balance(101));  // 2000
    System.out.println(bank.balance(102));  // 0
    System.out.println(bank);
    // Checking account 101: Joe First (1999) balance: 2000
    // Checking account 102: Jane Second (2001) balance: 0
    // Savings account 7: James Bond (1960) balance: 30
    // Checking account 8: James Bond (1960) balance: 0
    bank.processOrder(order1);
    bank.processOrder(order2);
    System.out.println(bank);
    // Checking account 101: Joe First (1999) balance: 3000
    // Checking account 102: Jane Second (2001) balance: 0
    // Savings account 7: James Bond (1960) balance: -470
    // Checking account 8: James Bond (1960) balance: 0
    // bank.processOrder(order2); lève une exception
    System.out.println(bank.auditByAccount());
    //{101=[CreditOrder[amount=1000, accountId=101]], 
    // 7=[DebitATMOrder[amount=500, accountId=7, visaId=567583]]}
  }

}
