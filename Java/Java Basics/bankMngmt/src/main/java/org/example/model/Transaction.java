package org.example.model;
import java.time.LocalDateTime;

public class Transaction {
    private String transactionID, accountNumber, type, timestamp;
    private double amount, balanceAfter;

    private static int transactionCounter = 0;

    public Transaction(
            String accountNumber,
            String type,
            double amount,
            double balanceAfter
    ){
            this.accountNumber = accountNumber;
            this.amount = amount;
            this.balanceAfter = balanceAfter;
            this.type = type;
            this.transactionID = String.format("TXN%03d", ++transactionCounter);
            this.timestamp = LocalDateTime.now().toString();
    }


    void displayTransactionDetails(){
        System.out.printf("");
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public double getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getTransactionID() {
        return transactionID;
    }
}
