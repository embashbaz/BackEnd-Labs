package org.example.sevices;

import org.example.models.Account;
import org.example.models.Transaction;

import java.util.List;

public class StatementGenerator {
    private final TransactionManager transactionManager;
    public StatementGenerator(TransactionManager transactionManager){
        this.transactionManager = transactionManager;
    }


    public void generateStatement(Account account){
        showAllTransactions(account.getAccountNumber());
        printSummary(account.getAccountNumber());
    }



    public void showAllTransactions(String accountNumber){
        var transaction = transactionManager.getTransactionsForAccount(accountNumber);
        if (transaction.isEmpty()) {
            System.out.println("No transactions recorded for this account.");
            return;
        }
        String HEADER = String.format(
                "%-8s | %-17s | %-17s | %-13s | %-13s%n" +
                        "%s%n",
                "TXN ID", "DATE/TIME", "TYPE", "AMOUNT", "BALANCE",
                "─".repeat(80));
        System.out.println(HEADER);

        for(Transaction txn:transaction){
            txn.displayTransactionDetails();
            System.out.println("─".repeat(80));
        }

    }

    private void printTransactions(List<Transaction> transactions){

    }

    private void printSummary(String accountNumber){
        double deps = transactionManager.calculateTotalDeposits(accountNumber);
        double wds  = transactionManager.calculateTotalWithdrawals(accountNumber);
        double net  = transactionManager.calculateNetChange(accountNumber);
        System.out.printf("Total Transactions: %d%n", transactionManager.getTransactionCount(accountNumber));
        System.out.printf("Total Deposits:     $%,.2f%n", deps);
        System.out.printf("Total Withdrawals:  $%,.2f%n", wds);
        System.out.printf("Net Change:         %s$%,.2f%n", net >= 0 ? "+" : "-", Math.abs(net));
    }
    private void printEmptyMessage(){

    }
}
