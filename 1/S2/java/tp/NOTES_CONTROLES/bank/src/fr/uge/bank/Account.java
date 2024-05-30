// https://monge.univ-mlv.fr/ens/Licence/L3/2021-2022/Java/exam.php
package fr.uge.bank;

public sealed interface Account permits CheckingAccount, SavingsAccount {
  
  final static int MAX_DATE_OF_BIRTH = 3000;
  
  int accountId();
  String name();
  int dateOfBirth();

}
