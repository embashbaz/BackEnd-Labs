package org.example.services;

import org.example.exceptions.InvalidAccountException;
import org.example.utils.BoundedList;
import org.example.models.Account;
import org.example.utils.Validator;

import static java.lang.String.format;


public class AccountManager {
    private BoundedList<Account> accounts = new BoundedList<>(50);

    private int accountCount = 0;


    public void addAccount(Account account){
        accounts.add(account);
        accountCount++;
    }

    public Account findAccount(String accountNumber){
        var account = accounts.stream().filter(e -> e.getAccountNumber().equalsIgnoreCase(accountNumber)).findFirst().orElse(null);
        if(account == null){
            throw new InvalidAccountException("Account not found");
        }
        return account;
    }

    public void transfer(String fromAcc, String toAcc, double amount){
        Account source = findAccount(fromAcc);
        Account dest = findAccount(toAcc);
        Validator.validateAmount(amount);

        double money = source.withdraw(amount);
        try {
            dest.deposit(money);
        } catch (Exception e){
            source.deposit(money);
            throw e;
        }
    }

    public void viewAllAccounts(){
        String HEADER = String.format(
                "%-8s | %-17s | %-17s | %-13s | %-6s%n" +
                        "%s%n",
                "ACC NO", "CUSTOMER NAME", "TYPE", "BALANCE", "STATUS",
                "─".repeat(73));
        System.out.println(HEADER);
        for(Account acc:accounts){
            System.out.print(String.format("%-8s | %-24s | %-17s | $%,12.2f | %-6s%n",
                    acc.getAccountNumber(),
                    acc.getCustomer().getName() + " (" + acc.getCustomer().getCustomerId() + ")",
                    acc.getAccountType(),
                    acc.getBalance(),
                    acc.getStatus()));
            System.out.print(format("         | %s%n", acc.displayAccountDetails()));
            System.out.println("─".repeat(73));
        }
        System.out.println( "Total Accounts: " + accountCount);
        System.out.printf("Total Bank Balance: $%,.2f%n", getTotalBalance());

    }

    public double getTotalBalance(){
        return accounts.stream().mapToDouble(Account::getBalance).sum();
    }

    public int getAccountCount(){
        return accounts.size();
    }
}
