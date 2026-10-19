package org.example.models;

import org.example.utils.Validator;

import java.util.Objects;

/**
 * Abstract base class for all bank account types.
 *
 * <p>An {@code Account} holds the customer, current balance, status, and an
 * auto-generated account number of the form {@code ACC001}. Concrete
 * subclasses ({@link SavingsAccount}, {@link CheckingAccount}) override
 * {@link #withdraw(double)} to enforce their own balance rules (minimum
 * balance, overdraft limit).</p>
 *
 * <p>The class implements {@link Transactable} so that any account can be
 * driven generically by a transaction type string ({@code "DEPOSIT"} or
 * {@code "WITHDRAWAL"}).</p>
 *
 * @see SavingsAccount
 * @see CheckingAccount
 * @see Transaction
 */
public abstract class Account implements Transactable {

    private String accountNumber;
    private Customer customer;
    private double balance;
    private String status;

    /** Static counter used to generate sequential account numbers. */
    static int accountCounter = 0;

    /**
     * Creates a new account and assigns it a unique account number.
     *
     * @param customer the account owner; must not be {@code null}
     * @param balance  the opening balance
     * @param status   the initial account status (e.g., {@code "Active"})
     */
    public Account(Customer customer, double balance, String status) {
        this.accountNumber = String.format("ACC%03d", ++accountCounter);
        this.customer = customer;
        this.balance = balance;
        this.status = status;
    }

    /**
     * Adds the given amount to the account balance.
     *
     * @param amount the amount to deposit; must be strictly positive
     * @return the deposited amount
     * @throws org.example.exceptions.InvalidAmountException if {@code amount <= 0}
     */
    public double deposit(double amount) {
        Validator.validateAmount(amount);
        balance = balance + amount;
        return amount;
    }

    /**
     * Subtracts the given amount from the account balance.
     *
     * <p>This base implementation does not enforce a minimum balance or
     * overdraft limit. Subclasses should override this method to add their
     * own rules and delegate back to {@code super.withdraw(amount)}.</p>
     *
     * @param amount the amount to withdraw; must be strictly positive
     * @return the withdrawn amount
     * @throws org.example.exceptions.InvalidAmountException if {@code amount <= 0}
     */
    public double withdraw(double amount) {
        Validator.validateAmount(amount);
        balance = balance - amount;
        return amount;
    }

    /**
     * Returns a one-line summary of account-type-specific details
     * (e.g., interest rate, overdraft limit, monthly fee).
     *
     * @return a formatted string describing the account type's parameters
     */
    public abstract String displayAccountDetails();

    /**
     * Returns the type label of the account (e.g., {@code "Savings"},
     * {@code "Checking"}).
     *
     * @return the account type
     */
    public abstract String getAccountType();

    /**
     * @return the auto-generated account number (e.g., {@code "ACC001"})
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /** Resets the static counter used for account-number generation. */
    public static void resetCounter() {
        accountCounter = 0;
    }

    /** @param accountNumber the new account number */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /** @return the customer who owns this account */
    public Customer getCustomer() {
        return customer;
    }

    /** @param customer the new account owner */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /** @return the current balance */
    public double getBalance() {
        return balance;
    }

    /** @param balance the new balance */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /** @return the account status (e.g., {@code "Active"}) */
    public String getStatus() {
        return status;
    }

    /** @param status the new account status */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Dispatches a transaction to {@link #deposit(double)} or
     * {@link #withdraw(double)} based on the given type.
     *
     * @param amount the transaction amount; must be strictly positive
     * @param type   {@code "DEPOSIT"} or {@code "WITHDRAWAL"}
     * @throws org.example.exceptions.InvalidAmountException if {@code amount <= 0}
     * @throws IllegalArgumentException if {@code type} is neither
     *         {@code "DEPOSIT"} nor {@code "WITHDRAWAL"}
     */
    @Override
    public void processTransaction(double amount, String type) {
        Validator.validateAmount(amount);
        if (Objects.equals(type, "WITHDRAWAL")) {
            withdraw(amount);
        } else if (Objects.equals(type, "DEPOSIT")) {
            deposit(amount);
        } else {
            throw new IllegalArgumentException();
        }
    }
}