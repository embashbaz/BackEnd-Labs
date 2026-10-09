package org.example.domain;

import org.example.utils.BoundedList;
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

    public void showAllTransactions(String accountNumber){
        var transaction = viewTransactions(accountNumber);
        if (transaction.isEmpty()) {
            System.out.println("No transactions recorded for this account.");
            return;
        }
        String HEADER = String.format(
                "%-8s | %-17s | %-17s | %-13s | %s%n" +
                        "%s%n",
                "TXN ID", "DATE/TIME", "TYPE", "AMOUNT", "BALANCE",
                "─".repeat(74));
        System.out.println(HEADER);

        for(Transaction txn:transaction){
            txn.displayTransactionDetails();
            System.out.println("─".repeat(74));
        }
        double deps = calculateTotalDeposits(accountNumber);
        double wds  = calculateTotalWithdrawals(accountNumber);
        double net  = deps - wds;
        System.out.printf("Total Transactions: %d%n", transaction.size());
        System.out.printf("Total Deposits:     $%,.2f%n", deps);
        System.out.printf("Total Withdrawals:  $%,.2f%n", wds);
        System.out.printf("Net Change:         %s$%,.2f%n", net >= 0 ? "+" : "-", Math.abs(net));
    }

    public List<Transaction> viewTransactions(String accountNumber){
        return transactions.stream().filter(e -> e.getAccountNumber().equals(accountNumber)).toList().reversed();
    }
}
