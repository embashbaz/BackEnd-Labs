package org.example.domain;

import org.example.BoundedList;
import org.example.model.Account;


public class AccountManager {
    private BoundedList<Account> accounts = new BoundedList<>(50);

    private int accountCount = 0;


    void addAccount(Account account){
        accounts.add(account);
        accountCount++;
    }

    Account findAccount(String accountNumber){
        return accounts.stream().filter(e -> e.getAccountNumber().equals(accountNumber)).findFirst().orElse(null);
    }

    void viewAllAccounts(){
        System.out.printf("""
                
                
                """);
    }

    double getTotalBalance(){
        return accounts.stream().mapToDouble(Account::getBalance).sum();
    }

    double getAccountCount(){
        return accounts.size();
    }
}
