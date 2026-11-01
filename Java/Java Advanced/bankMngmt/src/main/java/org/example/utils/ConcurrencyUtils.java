package org.example.utils;

import org.example.models.Account;
import org.example.services.AccountManager;

import java.util.stream.IntStream;

public final class ConcurrencyUtils {
    private ConcurrencyUtils() {}

    public static void runConcurrentSim(AccountManager am){
        Account target = am.getRandomAccount();
        System.out.printf("Running batch simulation on %s with balance $%,.2f%n", target.getAccountNumber(), target.getBalance());
        runBatchSimulation(target, 10, 500);

        System.out.println("Running multi-thread simulation on " + target.getAccountNumber());
        try {
            runSimulation(am, target.getAccountNumber());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Simulation interrupted.");
            return;
        }
        System.out.println();
        System.out.println("✓ Thread-safe operations completed successfully.");
        System.out.printf("Final Balance for %s: $%,.2f%n",
                target.getAccountNumber(), target.getBalance());
    }

    public static void runBatchSimulation(Account acc, int threads, double amount) {
        IntStream.range(0, threads).parallel()
                .forEach(i -> acc.deposit(amount));
    }

    public static void runSimulation(AccountManager am, String accNo) throws InterruptedException {
        Account acc = am.findAccount(accNo);
        Thread t1 = new Thread(() -> logDeposit(acc, 500, "Thread-1"), "Thread-1");
        Thread t2 = new Thread(() -> logDeposit(acc, 300, "Thread-2"), "Thread-2");
        Thread t3 = new Thread(() -> logWithdraw(acc, 200, "Thread-3"), "Thread-3");
        t1.start(); t2.start(); t3.start();
        t1.join();  t2.join();  t3.join();
    }

    private static void logDeposit(Account a, double amt, String thread) {
        System.out.printf("%s: Depositing $%.2f to %s%n", thread, amt, a.getAccountNumber());
        try {
            a.deposit(amt);
        } catch (Exception e) {
            System.out.printf("%s: ✗ %s%n", thread, e.getMessage());
        }
    }

    private static void logWithdraw(Account a, double amt, String thread) {
        System.out.printf("%s: Withdrawing $%.2f from %s%n", thread, amt, a.getAccountNumber());
        try {
            a.withdraw(amt);
        } catch (Exception e) {
            System.out.printf("%s: ✗ %s%n", thread, e.getMessage());
        }
    }
}