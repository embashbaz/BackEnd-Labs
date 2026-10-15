package org.example.models;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private String transactionID, accountNumber, type, timestamp;
    private double amount, balanceAfter;

    private static int transactionCounter = 0;

    public static final String ROW_FMT = "%-8s | %-17s | %-17s | %-13s | $%,12.2f%n";

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
            this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a"));
    }

    public static void resetCounter(){
        transactionCounter = 0;
    }

    public void displayTransactionDetails(){
        String signedAmount = (type.equals("DEPOSIT") ? "+" : "-")
                + String.format("$%,.2f", amount);
        System.out.print(String.format(ROW_FMT,
                getTransactionID(),
                getTimestamp(),
                getType(),
                signedAmount,
                getBalanceAfter()));
    }

    public static String nextTransactionId() {
        return String.format("TXN%03d", transactionCounter + 1);
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
