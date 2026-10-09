package org.example.domain;

import org.example.BoundedList;
import org.example.model.Account;
import org.example.model.Transaction;

public class TransactionManager {

    private BoundedList<Transaction> transactions = new BoundedList<>(200);
    private int transactionCount = 0;

    void addTransaction(Transaction transaction){
        transactions.add(transaction);
        transactionCount++;
    }
    double calculateTotalDeposits(String accountNumber){
        return transactions.stream().filter(e -> e.getType().equals("Deposit")).mapToDouble(Transaction::getAmount).sum();
    }
    double calculateTotalWithdrawals(String accountNumber){
        return transactions.stream().filter(e -> e.getType().equals("Withdrawal")).mapToDouble(Transaction::getAmount).sum();
    }
    double getTransactionCount(){
        return transactions.size();
    }
}
