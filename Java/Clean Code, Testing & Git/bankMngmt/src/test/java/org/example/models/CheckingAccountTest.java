package org.example.models;

import org.example.exceptions.OverdraftExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CheckingAccountTest {

    @BeforeEach
    void resetCounters() {
        Account.resetCounter();
        Customer.resetCounter();
        Transaction.resetCounter();
    }

    /** Helper: standard checking account for a Regular customer. */
    private CheckingAccount newRegularChecking(double balance) {
        return new CheckingAccount(
                new RegularCustomer("Regular User", "+1-555-1000", "1 Reg St", 30),
                balance, "Active");
    }

    /** Helper: checking account for a Premium customer (fee waived). */
    private CheckingAccount newPremiumChecking(double balance) {
        return new CheckingAccount(
                new PremiumCustomer("Premium User", "+1-555-2000", "1 Prem St", 40),
                balance, "Active");
    }

    @Test
    void overdraftWithinLimitAllowed() {
        CheckingAccount acc = newRegularChecking(500);
        acc.withdraw(1000);                    // 500 - 1000 = -500 (within -1000 limit)
        assertEquals(-500.0, acc.getBalance(), 0.001);
    }

    @Test
    void overdraftExactlyAtLimitAllowed() {
        CheckingAccount acc = newRegularChecking(500);
        acc.withdraw(1500);                    // 500 - 1500 = -1000 (exactly at limit)
        assertEquals(-1000.0, acc.getBalance(), 0.001);
    }

    @Test
    void overdraftExceedThrowsException() {
        CheckingAccount acc = newRegularChecking(500);
        assertThrows(OverdraftExceededException.class, () -> acc.withdraw(1600));
        assertEquals(500.0, acc.getBalance(), 0.001); // unchanged
    }

    @Test
    void withdrawNormalAmountWorks() {
        CheckingAccount acc = newRegularChecking(1000);
        acc.withdraw(300);
        assertEquals(700.0, acc.getBalance(), 0.001);
    }

    @Test
    void monthlyFeeAppliedForRegularCustomer() {
        CheckingAccount acc = newRegularChecking(1000);
        assertEquals(10.0, acc.getMonthlyFee(), 0.001);
    }

    @Test
    void monthlyFeeWaivedForPremiumCustomer() {
        CheckingAccount acc = newPremiumChecking(1000);
        assertEquals(0.0, acc.getMonthlyFee(), 0.001);
    }

    @Test
    void applyMonthlyFeeDeductsFee() {
        CheckingAccount acc = newRegularChecking(1000);
        acc.applyMonthlyFee();
        assertEquals(990.0, acc.getBalance(), 0.001);
    }
}