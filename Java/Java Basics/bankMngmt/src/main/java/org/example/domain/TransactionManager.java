package org.example.domain;

import org.example.BoundedList;
import org.example.model.Account;
import org.example.model.Transaction;

import java.util.List;

public class TransactionManager {

    private BoundedList<Transaction> transactions = new BoundedList<>(200);
    private int transactionCount = 0;

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
        transactionCount++;
    }
    public double calculateTotalDeposits(String accountNumber){
        return transactions.stream().filter(e -> e.getType().equals("DEPOSIT") && e.getAccountNumber().equals(accountNumber)).mapToDouble(Transaction::getAmount).sum();
    }
    public double calculateTotalWithdrawals(String accountNumber){
        return transactions.stream().filter(e -> e.getType().equals("WITHDRAWAL") && e.getAccountNumber().equals(accountNumber)).mapToDouble(Transaction::getAmount).sum();
    }
    public int getTransactionCount(){
        return transactions.size();
    }

    public List<Transaction> viewTransactions(String accountNumber){
        return transactions.stream().filter(e -> e.getAccountNumber().equals(accountNumber)).toList().reversed();
    }
}
