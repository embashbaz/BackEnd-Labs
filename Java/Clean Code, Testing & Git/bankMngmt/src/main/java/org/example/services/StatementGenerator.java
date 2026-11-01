package org.example.services;

import org.example.models.Account;
import org.example.models.Transaction;

import java.util.List;

/**
 * Formats and prints per-account statements to the console.
 *
 * <p>Statements include the account header (customer and balance), the
 * transaction ledger in newest-first order, and a summary of totals and
 * net change. Accounts with no transactions are handled gracefully.</p>
 *
 * <p>This class is presentation-only; it delegates all data access to
 * {@link TransactionManager}.</p>
 */
public class StatementGenerator {

    private final TransactionManager transactionManager;

    /**
     * Creates a statement generator backed by the given transaction store.
     *
     * @param transactionManager the source of transaction data; must not be
     *                           {@code null}
     */
    public StatementGenerator(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * Prints a statement for the given account.
     *
     * <p>If no transactions exist for the account, a short message is
     * printed and no header or summary is shown.</p>
     *
     * @param account the account to report on; must not be {@code null}
     */
    public void generateStatement(Account account) {
        List<Transaction> txns = transactionManager.getTransactionsForAccount(account.getAccountNumber());
        if (txns.isEmpty()) {
            System.out.println("No transactions recorded for this account.");
            return;
        }
        printHeader(account);
        printTransactions(txns);
        printSummary(account.getAccountNumber());
    }

    /**
     * Prints the account header (owner, type, balance) followed by the
     * transaction table column headings.
     *
     * @param account the account being reported on
     */
    private void printHeader(Account account) {
        System.out.println();
        System.out.println("Account: " + account.getCustomer().getName()
                + " (" + account.getAccountType() + ")");
        System.out.printf("Current Balance: $%,.2f%n%n", account.getBalance());
        String HEADER = String.format(
                "%-8s | %-17s | %-17s | %-13s | %-13s%n%s%n",
                "TXN ID", "DATE/TIME", "TYPE", "AMOUNT", "BALANCE",
                "─".repeat(80));
        System.out.println(HEADER);
    }

    /**
     * Prints each transaction in the given list, separated by a divider row.
     *
     * @param transactions the transactions to print; already ordered newest-first
     */
    private void printTransactions(List<Transaction> transactions) {
        for (Transaction txn : transactions) {
            txn.displayTransactionDetails();
            System.out.println("─".repeat(80));
        }
    }

    /**
     * Prints the summary block: total transaction count, total deposits,
     * total withdrawals, and net change.
     *
     * @param accountNumber the account number to summarise
     */
    private void printSummary(String accountNumber) {
        double deps = transactionManager.calculateTotalDeposits(accountNumber);
        double wds = transactionManager.calculateTotalWithdrawals(accountNumber);
        double net = transactionManager.calculateNetChange(accountNumber);
        System.out.printf("Total Transactions: %d%n",
                transactionManager.getTransactionCount(accountNumber));
        System.out.printf("Total Deposits:     $%,.2f%n", deps);
        System.out.printf("Total Withdrawals:  $%,.2f%n", wds);
        System.out.printf("Net Change:         %s$%,.2f%n",
                net >= 0 ? "+" : "-", Math.abs(net));
    }
}