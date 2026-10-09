package org.example.domain;

import org.example.utils.BoundedList;
import org.example.model.Account;

import static java.lang.String.format;


public class AccountManager {
    private BoundedList<Account> accounts = new BoundedList<>(50);

    private int accountCount = 0;


    public void addAccount(Account account){
        accounts.add(account);
        accountCount++;
    }

    public Account findAccount(String accountNumber){
        return accounts.stream().filter(e -> e.getAccountNumber().equals(accountNumber)).findFirst().orElse(null);
    }

    public void viewAllAccounts(){
        String HEADER = """
    ACC NO   | CUSTOMER NAME     | TYPE              | BALANCE      | STATUS
    ──────────────────────────────────────────────────────────────────────────
    """;
        System.out.println(HEADER);
        for(Account acc:accounts){
            System.out.print(format("%-8s | %-17s | %-17s | $%,12.2f | %-6s%n",
                    acc.getAccountNumber(),
                    acc.getCustomer().getName(),
                    acc.getAccountType(),
                    acc.getBalance(),
                    acc.getStatus()));
            System.out.print(format("         | %s%n", acc.displayAccountDetails()));
            System.out.println("─".repeat(74));
        }
        System.out.println( "Total Accounts: " + accountCount);
        System.out.printf("Total Bank Balance: $%,.2f%n", getTotalBalance());

    }

    double getTotalBalance(){
        return accounts.stream().mapToDouble(Account::getBalance).sum();
    }

    int getAccountCount(){
        return accounts.size();
    }
}
