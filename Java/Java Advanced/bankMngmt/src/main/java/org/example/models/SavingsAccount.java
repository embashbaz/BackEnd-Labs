package org.example.models;

import org.example.exceptions.InsufficientFundsException;

/**
 * A savings account that earns interest and enforces a minimum balance.
 *
 * <p>Withdrawals are rejected if they would leave the balance below
 * the fixed minimum of {@value #MINIMUM_BALANCE} dollars.</p>
 */
public class SavingsAccount extends Account {

    /** Annual interest rate applied by {@link #calculateInterest()}, in percent. */
    private final double interestRate;

    /** Minimum balance that must remain after any withdrawal. */
    private final double minimumBalance;

    private static final double DEFAULT_INTEREST_RATE = 3.5;
    private static final double MINIMUM_BALANCE = 500;

    /**
     * Creates a savings account with the default interest rate and minimum
     * balance.
     *
     * @param customer the account owner
     * @param balance  the opening balance
     * @param status   the initial account status
     */
    public SavingsAccount(Customer customer, double balance, String status) {
        super(customer, balance, status);
        this.interestRate = DEFAULT_INTEREST_RATE;
        this.minimumBalance = MINIMUM_BALANCE;
    }

    /**
     * Withdraws the given amount if the resulting balance would not fall
     * below {@link #minimumBalance}.
     *
     * @param amount the amount to withdraw
     * @return the withdrawn amount
     * @throws InsufficientFundsException if the withdrawal would drop the
     *         balance below the minimum
     * @throws org.example.exceptions.InvalidAmountException if {@code amount <= 0}
     */
    @Override
    public double withdraw(double amount) {
        if ((getBalance() - amount) < minimumBalance) {
            throw new InsufficientFundsException(
                    "You cannot withdraw beyond the allowed limit");
        }
        return super.withdraw(amount);
    }

    /**
     * @return a string of the form {@code "Interest Rate: 3.5% | Min Balance: $500.00"}
     */
    @Override
    public String displayAccountDetails() {
        return "Interest Rate: 3.5% | Min Balance: $500.00";
    }

    /** @return {@code "Savings"} */
    @Override
    public String getAccountType() {
        return "Savings";
    }

    /**
     * Calculates the interest accrued on the current balance for one year.
     *
     * @return the interest amount, equal to {@code balance * interestRate / 100}
     */
    public double calculateInterest() {
        return this.getBalance() * (interestRate / 100);
    }
}