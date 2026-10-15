package org.example.services;

import org.example.utils.BoundedList;
import org.example.models.Transaction;

import java.util.List;

public class TransactionManager {


    private BoundedList<Transaction> transactions = new BoundedList<>(200);

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }
    public double calculateTotalDeposits(String accountNumber){
        return transactions.stream().filter(e -> e.getType().equals("DEPOSIT") && e.getAccountNumber().equals(accountNumber)).mapToDouble(Transaction::getAmount).sum();
    }
    public double calculateTotalWithdrawals(String accountNumber){
        return transactions.stream().filter(e -> e.getType().equals("WITHDRAWAL") && e.getAccountNumber().equals(accountNumber)).mapToDouble(Transaction::getAmount).sum();
    }

    public double calculateNetChange(String accountNumber){
        double deps = calculateTotalDeposits(accountNumber);
        double wds  = calculateTotalWithdrawals(accountNumber);
        return deps - wds;
    }
    public int getTransactionCount(String accountNumber){
         return transactions.stream().filter(e -> e.getAccountNumber().equals(accountNumber)).toList().size();
    }


    public List<Transaction> getTransactionsForAccount(String accountNumber){
        return transactions.stream().filter(e -> e.getAccountNumber().equals(accountNumber)).toList().reversed();
    }
}
