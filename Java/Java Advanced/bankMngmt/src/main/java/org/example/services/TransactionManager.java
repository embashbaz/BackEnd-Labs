package org.example.services;

import org.example.utils.BoundedList;
import org.example.models.Transaction;
import org.example.utils.FunctionalUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores and aggregates {@link Transaction}s recorded during the session.
 *
 * <p>This service is purely data-oriented: it does not print anything.
 * Formatting and display of statements is handled by
 * {@link StatementGenerator}. Transactions are held in a
 * {@link BoundedList} capped at 200 entries.</p>
 */
public class TransactionManager {

    private final ArrayList<Transaction> transactions = new ArrayList<>();

    /**
     * Records a transaction.
     *
     * @param transaction the transaction to store; must not be {@code null}
     * @throws IllegalStateException if the internal capacity (200) is exceeded
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    /**
     * Sums all deposits recorded for the given account.
     *
     * @param accountNumber the account number to filter on
     * @return the total deposited amount; {@code 0.0} if none
     */
    public double calculateTotalDeposits(String accountNumber) {
        return transactions.stream()
                .filter(FunctionalUtils.isDeposit().and(FunctionalUtils.forAccount(accountNumber)))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    /**
     * Sums all withdrawals recorded for the given account.
     *
     * @param accountNumber the account number to filter on
     * @return the total withdrawn amount; {@code 0.0} if none
     */
    public double calculateTotalWithdrawals(String accountNumber) {
        return transactions.stream()
                .filter(FunctionalUtils.isWithdrawal().and(FunctionalUtils.forAccount(accountNumber)))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    /**
     * Computes the net change (deposits − withdrawals) for the given account.
     *
     * @param accountNumber the account number to filter on
     * @return the net change; may be negative
     */
    public double calculateNetChange(String accountNumber) {
        double deps = calculateTotalDeposits(accountNumber);
        double wds = calculateTotalWithdrawals(accountNumber);
        return deps - wds;
    }

    /**
     * Counts the transactions recorded for the given account.
     *
     * @param accountNumber the account number to filter on
     * @return the number of matching transactions; {@code 0} if none
     */
    public int getTransactionCount(String accountNumber) {
        return transactions.stream()
                .filter(FunctionalUtils.forAccount(accountNumber))
                .toList()
                .size();
    }

    /**
     * Returns all transactions for the given account, newest first.
     *
     * @param accountNumber the account number to filter on
     * @return a list of matching transactions in reverse insertion order
     */
    public List<Transaction> getTransactionsForAccount(String accountNumber) {
        return transactions.stream()
                .filter(FunctionalUtils.forAccount(accountNumber))
                .sorted(FunctionalUtils.byDateDescending())
                .toList();
    }

    public List<Transaction> getAllTransactions() {
        return List.copyOf(transactions);
    }
}