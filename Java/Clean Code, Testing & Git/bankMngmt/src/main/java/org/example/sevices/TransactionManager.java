package org.example.sevices;

import org.example.utils.BoundedList;
import org.example.models.Transaction;

import java.util.List;

public class TransactionManager {
    private final AccountManager accountManager;
    public TransactionManager(AccountManager accountManager){
        this.accountManager = accountManager;
    }

    private BoundedList<Transaction> transactions = new BoundedList<>(200);
    private int transactionCount = 0;

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
        transactionCount++;
    }
    public double calculateTotalDeposits(String accountNumber){
        accountManager.findAccount(accountNumber);
        return transactions.stream().filter(e -> e.getType().equals("DEPOSIT") && e.getAccountNumber().equals(accountNumber)).mapToDouble(Transaction::getAmount).sum();
    }
    public double calculateTotalWithdrawals(String accountNumber){
        accountManager.findAccount(accountNumber);
        return transactions.stream().filter(e -> e.getType().equals("WITHDRAWAL") && e.getAccountNumber().equals(accountNumber)).mapToDouble(Transaction::getAmount).sum();
    }

    public double calculateNetChange(String accountNumber){
        accountManager.findAccount(accountNumber);
        double deps = calculateTotalDeposits(accountNumber);
        double wds  = calculateTotalWithdrawals(accountNumber);
        return deps - wds;
    }
    public int getTransactionCount(String accountNumber){
        accountManager.findAccount(accountNumber);
         return transactions.stream().filter(e -> e.getAccountNumber().equals(accountNumber)).toList().size();
    }


    public List<Transaction> getTransactionsForAccount(String accountNumber){
        accountManager.findAccount(accountNumber);
        return transactions.stream().filter(e -> e.getAccountNumber().equals(accountNumber)).toList().reversed();
    }
}
