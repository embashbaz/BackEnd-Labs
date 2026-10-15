package org.example.services;

import org.example.models.Account;
import org.example.models.Customer;
import org.example.models.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionManagerTest {

    private TransactionManager transactionManager;

    @BeforeEach
    void setUp() {
        Account.resetCounter();
        Customer.resetCounter();
        Transaction.resetCounter();
        transactionManager = new TransactionManager();
    }

    /** Helper: build a transaction with a placeholder balance. */
    private Transaction txn(String accountNumber, String type, double amount) {
        return new Transaction(accountNumber, type, amount, 0);
    }

    @Test
    void addTransactionIncreasesCount() {
        assertEquals(0, transactionManager.getTransactionCount("ACC001"));

        transactionManager.addTransaction(txn("ACC001", "DEPOSIT", 100));

        assertEquals(1, transactionManager.getTransactionCount("ACC001"));
    }

    @Test
    void getTransactionsForAccountFiltersCorrectly() {
        transactionManager.addTransaction(txn("ACC001", "DEPOSIT", 100));
        transactionManager.addTransaction(txn("ACC002", "DEPOSIT", 200));
        transactionManager.addTransaction(txn("ACC001", "WITHDRAWAL", 50));

        List<Transaction> acc1 = transactionManager.getTransactionsForAccount("ACC001");

        assertEquals(2, acc1.size());
        assertTrue(acc1.stream().allMatch(t -> t.getAccountNumber().equals("ACC001")));
    }

    @Test
    void getTransactionsForAccountReturnsNewestFirst() {
        transactionManager.addTransaction(txn("ACC001", "DEPOSIT", 100));   // TXN001
        transactionManager.addTransaction(txn("ACC001", "DEPOSIT", 200));   // TXN002
        transactionManager.addTransaction(txn("ACC001", "DEPOSIT", 300));   // TXN003

        List<Transaction> acc1 = transactionManager.getTransactionsForAccount("ACC001");

        assertEquals("TXN003", acc1.get(0).getTransactionID());
        assertEquals("TXN002", acc1.get(1).getTransactionID());
        assertEquals("TXN001", acc1.get(2).getTransactionID());
    }

    @Test
    void calculateTotalDepositsSumsOnlyDeposits() {
        transactionManager.addTransaction(txn("ACC001", "DEPOSIT", 100));
        transactionManager.addTransaction(txn("ACC001", "DEPOSIT", 200));
        transactionManager.addTransaction(txn("ACC001", "WITHDRAWAL", 50));

        assertEquals(300.0,
                transactionManager.calculateTotalDeposits("ACC001"), 0.001);
    }

    @Test
    void calculateTotalWithdrawalsSumsOnlyWithdrawals() {
        transactionManager.addTransaction(txn("ACC001", "DEPOSIT", 100));
        transactionManager.addTransaction(txn("ACC001", "WITHDRAWAL", 50));
        transactionManager.addTransaction(txn("ACC001", "WITHDRAWAL", 25));

        assertEquals(75.0,
                transactionManager.calculateTotalWithdrawals("ACC001"), 0.001);
    }

    @Test
    void calculateNetChangeCorrect() {
        transactionManager.addTransaction(txn("ACC001", "DEPOSIT", 500));
        transactionManager.addTransaction(txn("ACC001", "WITHDRAWAL", 200));

        assertEquals(300.0,
                transactionManager.calculateNetChange("ACC001"), 0.001);
    }

    @Test
    void calculateTotalsForUnknownAccountReturnsZero() {
        transactionManager.addTransaction(txn("ACC001", "DEPOSIT", 100));

        assertEquals(0.0,
                transactionManager.calculateTotalDeposits("ACC999"), 0.001);
        assertEquals(0.0,
                transactionManager.calculateTotalWithdrawals("ACC999"), 0.001);
        assertEquals(0.0,
                transactionManager.calculateNetChange("ACC999"), 0.001);
    }

    @Test
    void getTransactionCountForEmptyAccountIsZero() {
        transactionManager.addTransaction(txn("ACC001", "DEPOSIT", 100));

        assertEquals(0, transactionManager.getTransactionCount("ACC999"));
    }
}