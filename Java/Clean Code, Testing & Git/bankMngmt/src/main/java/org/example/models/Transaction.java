package org.example.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Immutable record of a single deposit or withdrawal for one account.
 *
 * <p>Each transaction has an auto-generated ID of the form {@code TXN001},
 * a timestamp captured at construction, the account it belongs to, its
 * type, the amount, and the account balance immediately after the
 * transaction was applied.</p>
 */
public class Transaction {

    /** Format string used to align transaction rows in console output. */
    public static final String ROW_FMT =
            "%-8s | %-17s | %-17s | %-13s | $%,12.2f%n";

    private String transactionID;
    private String accountNumber;
    private String type;
    private String timestamp;
    private double amount;
    private double balanceAfter;

    private static int transactionCounter = 0;

    /**
     * Creates a transaction and assigns it a unique transaction ID and
     * the current timestamp.
     *
     * @param accountNumber the account this transaction belongs to
     * @param type          {@code "DEPOSIT"} or {@code "WITHDRAWAL"}
     * @param amount        the transaction amount
     * @param balanceAfter  the account balance immediately after the transaction
     */
    public Transaction(String accountNumber, String type, double amount, double balanceAfter) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.type = type;
        this.transactionID = String.format("TXN%03d", ++transactionCounter);
        this.timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a"));
    }

    /** Resets the static counter used for transaction-ID generation. */
    public static void resetCounter() {
        transactionCounter = 0;
    }

    /**
     * Prints this transaction as a single aligned row to {@code System.out}.
     * Deposits are shown with a leading {@code +} and withdrawals with
     * {@code -}.
     */
    public void displayTransactionDetails() {
        String signedAmount = (type.equals("DEPOSIT") ? "+" : "-")
                + String.format("$%,.2f", amount);
        System.out.print(String.format(ROW_FMT,
                getTransactionID(),
                getTimestamp(),
                getType(),
                signedAmount,
                getBalanceAfter()));
    }

    /**
     * @return the ID that the next transaction will receive, without
     *         incrementing the counter
     */
    public static String nextTransactionId() {
        return String.format("TXN%03d", transactionCounter + 1);
    }

    /** @return the account balance after this transaction was applied */
    public double getBalanceAfter() {
        return balanceAfter;
    }

    /** @return the transaction amount */
    public double getAmount() {
        return amount;
    }

    /** @return the timestamp as a formatted string */
    public String getTimestamp() {
        return timestamp;
    }

    /** @return the transaction type ({@code "DEPOSIT"} or {@code "WITHDRAWAL"}) */
    public String getType() {
        return type;
    }

    /** @return the account number this transaction belongs to */
    public String getAccountNumber() {
        return accountNumber;
    }

    /** @return the auto-generated transaction ID (e.g., {@code "TXN001"}) */
    public String getTransactionID() {
        return transactionID;
    }
}