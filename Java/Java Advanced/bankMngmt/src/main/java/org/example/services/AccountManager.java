package org.example.services;

import org.example.exceptions.InvalidAccountException;
import org.example.models.Account;
import org.example.utils.ValidationUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static java.lang.String.format;

/**
 * Manages the collection of {@link Account}s held by the bank.
 *
 * <p>Provides account creation, lookup, transfer, and a formatted
 * listing of all accounts. Lookups are case-insensitive on the
 * account number.</p>
 *
 * @see Account
 * @see TransactionManager
 */
public class AccountManager {

    private final HashMap<String, Account> accounts = new HashMap<>();

    /** Running count of accounts added through {@link #addAccount(Account)}. */


    /**
     * Adds a new account to the bank.
     *
     * @param account the account to add; must not be {@code null}
     * @throws IllegalStateException if the internal capacity (50) is exceeded
     */
    public void addAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    /**
     * Finds an account by its account number (case-insensitive).
     *
     * @param accountNumber the account number to search for
     * @return the matching {@link Account}
     * @throws InvalidAccountException if no account matches
     */
    public Account findAccount(String accountNumber) {
        var account = accounts.get(accountNumber.toUpperCase());
        if (account == null) {
            throw new InvalidAccountException("Account not found");
        }
        return account;
    }

    /**
     * Transfers the given amount from one account to another.
     *
     * <p>Both accounts must exist and the amount must be strictly positive.
     * If the destination deposit fails for any reason, the source withdrawal
     * is rolled back before the exception is re-thrown.</p>
     *
     * @param fromAcc the source account number
     * @param toAcc   the destination account number
     * @param amount  the amount to transfer; must be strictly positive
     * @throws InvalidAccountException if either account does not exist
     * @throws org.example.exceptions.InvalidAmountException if {@code amount <= 0}
     * @throws org.example.exceptions.InsufficientFundsException if the source
     *         is a savings account with insufficient balance
     * @throws org.example.exceptions.OverdraftExceededException if the source
     *         is a checking account and the overdraft limit would be exceeded
     */
    public void transfer(String fromAcc, String toAcc, double amount) {
        Account source = findAccount(fromAcc);
        Account dest = findAccount(toAcc);
        ValidationUtils.validateAmount(amount);

        double money = source.withdraw(amount);
        try {
            dest.deposit(money);
        } catch (Exception e) {
            source.deposit(money);
            throw e;
        }
    }

    /**
     * Prints a tabular list of all accounts with their balances, status,
     * and type-specific details, followed by totals.
     */
    public void viewAllAccounts() {
        String HEADER = String.format(
                "%-8s | %-17s | %-17s | %-13s | %-6s%n" +
                        "%s%n",
                "ACC NO", "CUSTOMER NAME", "TYPE", "BALANCE", "STATUS",
                "─".repeat(73));
        System.out.println(HEADER);
        for (Account acc : accounts.values().stream().sorted(Comparator.comparing(Account::getAccountNumber)).toList()) {
            System.out.print(String.format("%-8s | %-24s | %-17s | $%,12.2f | %-6s%n",
                    acc.getAccountNumber(),
                    acc.getCustomer().getName() + " (" + acc.getCustomer().getCustomerId() + ")",
                    acc.getAccountType(),
                    acc.getBalance(),
                    acc.getStatus()));
            System.out.print(format("         | %s%n", acc.displayAccountDetails()));
            System.out.println("─".repeat(73));
        }
        System.out.println("Total Accounts: " + getAccountCount());
        System.out.printf("Total Bank Balance: $%,.2f%n", getTotalBalance());
    }

    /**
     * @return the sum of all account balances currently held by the bank
     */
    public double getTotalBalance() {
        return accounts.values().stream().mapToDouble(Account::getBalance).sum();
    }

    /**
     * @return the number of accounts currently managed
     */
    public int getAccountCount() {
        return accounts.size();
    }

    public List<Account> getAllAccounts() {
        return accounts.values().stream()
                .sorted(Comparator.comparing(Account::getAccountNumber))
                .toList();
    }
}