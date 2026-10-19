package org.example.models;

import org.example.exceptions.OverdraftExceededException;

/**
 * A checking account that supports overdraft up to a fixed limit and may
 * charge a monthly fee.
 *
 * <p>Regular customers pay a {@value #REGULAR_MONTHLY_FEE} dollar monthly
 * fee; {@link PremiumCustomer}s have the fee waived.</p>
 */
public class CheckingAccount extends Account {

    /** Maximum negative balance allowed before withdrawals are rejected. */
    private final double overdraftLimit;

    /** Monthly fee applied by {@link #applyMonthlyFee()}. */
    private final double monthlyFee;

    private static final double OVERDRAFT_LIMIT = 1000;
    private static final double REGULAR_MONTHLY_FEE = 10;

    /**
     * Creates a checking account with the default overdraft limit and a
     * monthly fee that depends on the customer type.
     *
     * @param customer the account owner
     * @param balance  the opening balance
     * @param status   the initial account status
     */
    public CheckingAccount(Customer customer, double balance, String status) {
        super(customer, balance, status);
        this.overdraftLimit = OVERDRAFT_LIMIT;
        if (getCustomer() instanceof PremiumCustomer pc && pc.hasWaivedFees()) {
            this.monthlyFee = 0;
        } else {
            this.monthlyFee = REGULAR_MONTHLY_FEE;
        }
    }

    /**
     * Withdraws the given amount if the resulting balance would not fall
     * below {@code -overdraftLimit}.
     *
     * @param amount the amount to withdraw
     * @return the withdrawn amount
     * @throws OverdraftExceededException if the withdrawal would exceed the
     *         overdraft limit
     * @throws org.example.exceptions.InvalidAmountException if {@code amount <= 0}
     */
    @Override
    public double withdraw(double amount) {
        if ((getBalance() - amount) < (0 - overdraftLimit)) {
            throw new OverdraftExceededException(
                    "You can not withdraw beyond the allowed limit");
        }
        return super.withdraw(amount);
    }

    /**
     * @return a string describing the overdraft limit and monthly fee
     *         (including a "waived" note for Premium customers)
     */
    @Override
    public String displayAccountDetails() {
        String fee = (monthlyFee == 0)
                ? "$0.00 (WAIVED - Premium Customer)"
                : String.format("$%.2f", monthlyFee);
        return String.format("Overdraft Limit: $%,.2f | Monthly Fee: %s",
                overdraftLimit, fee);
    }

    /** @return {@code "Checking"} */
    @Override
    public String getAccountType() {
        return "Checking";
    }

    /**
     * Deducts the monthly fee from the current balance.
     */
    public void applyMonthlyFee() {
        setBalance(getBalance() - monthlyFee);
    }

    /** @return the monthly fee charged on this account (may be 0) */
    public double getMonthlyFee() {
        return monthlyFee;
    }
}