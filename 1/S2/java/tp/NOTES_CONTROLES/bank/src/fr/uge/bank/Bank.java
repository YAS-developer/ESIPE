package fr.uge.bank;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Bank {
  
  private final ArrayList<Account> accounts;
  private final HashMap<Integer, Integer> balance;
  private final LinkedHashSet<Order> audit;
  
  public Bank() {
    accounts = new ArrayList<>();
    balance = new HashMap<>();
    audit = new LinkedHashSet<>();
  }

  public void addAccount(Account account) {
    Objects.requireNonNull(account);
    if (balance.putIfAbsent(account.accountId(), 0) != null) {
      throw new IllegalArgumentException("account already exists");
    }
    accounts.add(account);
  }
  
  
  public List<Account> findBornAfter(int dateOfBirth){
    return accounts.stream()
                   .filter(account -> account.dateOfBirth() >= dateOfBirth)
                   .sorted(Comparator.comparing(Account::name))
                   .toList();
  }
  
  private static boolean isCheckingAccount(Account account) {
    return switch (account) {
      case CheckingAccount c -> true ;
      case SavingsAccount c -> false;
    };
  }
  
  public List<Account> findCheckingAccounts(){
    return accounts.stream()
                   .filter(Bank::isCheckingAccount)
                   .toList();
  }
  
  public Map<String, List<Account>> accountsByName(){
    return accounts.stream()
                   .collect(Collectors.groupingBy(Account::name));
  }
  
  
  public void changeBalance(int amount, int accountId) {
    if (! balance.containsKey(accountId)) {
      throw new IllegalArgumentException("account does not exist");
    }
    balance.merge(accountId, amount, Math::addExact);
  }
  
  public int balance(int accountId) {
    var b = balance.getOrDefault(accountId, null);
    if (b == null) {
      throw new IllegalArgumentException("account does not exist");
    }
    return b;
  }
  
  // avec le pattern matching de Java 19 ici
  public void processOrder(Order order) {
    Objects.requireNonNull(order);
    if (! audit.add(order)) {
      throw new IllegalArgumentException("order already performed");
    }
    switch (order) {
      case CreditOrder d -> changeBalance(d.amount(), d.accountId());
      case DebitATMOrder d -> changeBalance(- d.amount(), d.accountId());
    }
  }
  
  public Map<Integer, List<Order>> auditByAccount(){
    return audit.stream()
                .collect(Collectors.groupingBy(Order::accountId));
  }
  
  
//  @Override
//  public String toString() {
//    return accounts.stream()
//                  .map(Account::toString)
//                  .collect(Collectors.joining("\n"));
//  }
  
  
  @Override
  public String toString() {
    return accounts.stream()
                  .map(account -> account + " balance: " + balance.get(account.accountId()))
                  .collect(Collectors.joining("\n"));
  }
}
