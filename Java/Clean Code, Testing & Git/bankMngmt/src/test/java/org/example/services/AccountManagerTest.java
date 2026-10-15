package org.example.services;

import org.example.exceptions.InvalidAccountException;
import org.example.exceptions.InvalidAmountException;
import org.example.exceptions.OverdraftExceededException;
import org.example.exceptions.InsufficientFundsException;
import org.example.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountManagerTest {

    private AccountManager accountManager;

    @BeforeEach
    void setUp() {
        Account.resetCounter();
        Customer.resetCounter();
        Transaction.resetCounter();
        accountManager = new AccountManager();
    }

    /* ---------- helpers ---------- */

    private Account newChecking(String name, double balance) {
        return new CheckingAccount(
                new RegularCustomer(name, "+1-555-0000", "1 St", 30),
                balance, "Active");
    }

    private Account newSavings(String name, double balance) {
        return new SavingsAccount(
                new RegularCustomer(name, "+1-555-0000", "1 St", 30),
                balance, "Active");
    }

    /* ---------- findAccount ---------- */

    @Test
    void findAccountThrowsOnUnknownAccount() {
        assertThrows(InvalidAccountException.class,
                () -> accountManager.findAccount("ACC999"));
    }

    @Test
    void findAccountIsCaseInsensitive() {
        Account acc = newChecking("Alice", 1000);
        accountManager.addAccount(acc);

        Account found = accountManager.findAccount("acc001"); // lowercase
        assertNotNull(found);
        assertEquals(acc.getAccountNumber(), found.getAccountNumber());
    }

    /* ---------- addAccount ---------- */

    @Test
    void addAccountIncreasesCount() {
        assertEquals(0, accountManager.getAccountCount());
        accountManager.addAccount(newChecking("Alice", 1000));
        assertEquals(1, accountManager.getAccountCount());
        accountManager.addAccount(newChecking("Bob", 1000));
        assertEquals(2, accountManager.getAccountCount());
    }

    /* ---------- transfer ---------- */

    @Test
    void transferMovesMoneyCorrectly() {
        Account source = newChecking("Source", 1000);
        Account dest   = newChecking("Dest",   500);
        accountManager.addAccount(source);
        accountManager.addAccount(dest);

        accountManager.transfer(source.getAccountNumber(),
                dest.getAccountNumber(), 300);

        assertEquals(700.0, source.getBalance(), 0.001);
        assertEquals(800.0, dest.getBalance(),   0.001);
    }

    @Test
    void transferFromSavingsBelowMinimumThrows() {
        Account source = newSavings("Saver", 1000);
        Account dest   = newChecking("Dest", 500);
        accountManager.addAccount(source);
        accountManager.addAccount(dest);

        assertThrows(InsufficientFundsException.class,
                () -> accountManager.transfer(
                        source.getAccountNumber(),
                        dest.getAccountNumber(), 600));

        // source and dest unchanged
        assertEquals(1000.0, source.getBalance(), 0.001);
        assertEquals(500.0,  dest.getBalance(),   0.001);
    }

    @Test
    void transferFromCheckingBeyondOverdraftThrows() {
        Account source = newChecking("Spender", 500);
        Account dest   = newChecking("Dest",    500);
        accountManager.addAccount(source);
        accountManager.addAccount(dest);

        assertThrows(OverdraftExceededException.class,
                () -> accountManager.transfer(
                        source.getAccountNumber(),
                        dest.getAccountNumber(), 2000));

        assertEquals(500.0, source.getBalance(), 0.001);
        assertEquals(500.0, dest.getBalance(),   0.001);
    }

    @Test
    void transferInvalidAmountThrows() {
        Account source = newChecking("Source", 1000);
        Account dest   = newChecking("Dest",   1000);
        accountManager.addAccount(source);
        accountManager.addAccount(dest);

        assertThrows(InvalidAmountException.class,
                () -> accountManager.transfer(
                        source.getAccountNumber(),
                        dest.getAccountNumber(), -100));

        assertEquals(1000.0, source.getBalance(), 0.001);
        assertEquals(1000.0, dest.getBalance(),   0.001);
    }

    @Test
    void transferToUnknownAccountThrows() {
        Account source = newChecking("Source", 1000);
        accountManager.addAccount(source);

        assertThrows(InvalidAccountException.class,
                () -> accountManager.transfer(
                        source.getAccountNumber(), "ACC999", 100));

        assertEquals(1000.0, source.getBalance(), 0.001);
    }

    @Test
    void transferRollsBackOnDestinationFailure() {
        Account source = newChecking("Source", 1000);

        // A stub Account whose deposit() always throws.
        Account failingDest = new Account(
                new RegularCustomer("Broken", "+1-555-9999", "1 X St", 30),
                500, "Active") {

            @Override
            public String displayAccountDetails() { return "stub"; }

            @Override
            public String getAccountType() { return "Stub"; }

            @Override
            public double deposit(double amount) {
                throw new RuntimeException("simulated deposit failure");
            }
        };

        accountManager.addAccount(source);
        accountManager.addAccount(failingDest);

        assertThrows(RuntimeException.class,
                () -> accountManager.transfer(
                        source.getAccountNumber(),
                        failingDest.getAccountNumber(), 300));

        // Source must be restored after the failed deposit
        assertEquals(1000.0, source.getBalance(), 0.001);
    }
}