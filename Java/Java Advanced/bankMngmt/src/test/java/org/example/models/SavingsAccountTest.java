package org.example.models;

import org.example.exceptions.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SavingsAccountTest {

    private static final double MINIMUM_BALANCE = 500.0;

    @BeforeEach
    void resetCounters() {
        Account.resetCounter();
        Customer.resetCounter();
        Transaction.resetCounter();
    }

    /** Helper: savings account with a Regular customer. */
    private SavingsAccount newSavings(double balance) {
        return new SavingsAccount(
                new RegularCustomer("Saver", "+1-555-3000", "3 Sav St", 35),
                balance, "Active");
    }

    @Test
    void withdrawAboveMinimumAllowed() {
        SavingsAccount acc = newSavings(1000);
        acc.withdraw(400);                       // 1000 - 400 = 600 (> 500)
        assertEquals(600.0, acc.getBalance(), 0.001);
    }

    @Test
    void withdrawExactlyToMinimumAllowed() {
        SavingsAccount acc = newSavings(1000);
        acc.withdraw(500);                       // 1000 - 500 = 500 (exactly minimum)
        assertEquals(500.0, acc.getBalance(), 0.001);
    }

    @Test
    void withdrawBelowMinimumThrowsException() {
        SavingsAccount acc = newSavings(1000);
        assertThrows(InsufficientFundsException.class, () -> acc.withdraw(600));
        assertEquals(1000.0, acc.getBalance(), 0.001); // unchanged
    }

    @Test
    void withdrawEntireBalanceThrowsException() {
        SavingsAccount acc = newSavings(1000);
        assertThrows(InsufficientFundsException.class, () -> acc.withdraw(1000));
        assertEquals(1000.0, acc.getBalance(), 0.001);
    }

    @Test
    void calculateInterestReturnsCorrectAmount() {
        SavingsAccount acc = newSavings(1000);
        assertEquals(35.0, acc.calculateInterest(), 0.001); // 1000 * 3.5 / 100
    }

    @Test
    void depositThenWithdrawWithinLimitsWorks() {
        SavingsAccount acc = newSavings(1000);
        acc.deposit(500);                        // balance = 1500
        acc.withdraw(300);                       // balance = 1200 (still > 500)
        assertEquals(1200.0, acc.getBalance(), 0.001);
    }
}