package org.example;

import org.example.domain.AccountManager;
import org.example.domain.TransactionManager;
import org.example.model.*;

import static org.example.utils.InputParser.*;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final AccountManager accounts = new AccountManager();
    private static final TransactionManager txns = new TransactionManager();

    public static void main(String[] args) {
        seedAccounts();
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> createAccount();
                case 2 -> viewAccounts();
                case 3 -> processTransaction();
                case 4 -> viewTransactionHistory();
                case 5 -> running = false;
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
        System.out.println("Thank you for using Bank Account Management System!");
        System.out.println("Goodbye!");
    }

    private static void seedAccounts() {
        accounts.addAccount(new SavingsAccount(
                new RegularCustomer("John Smith", "+1-555-0101",
                        "12 Elm Street, Springfield", 34),
                5250.00, "Active"));

        accounts.addAccount(new CheckingAccount(
                new RegularCustomer("Sarah Johnson", "+1-555-0102",
                        "88 Pine Road, Springfield", 29),
                3450.00, "Active"));

        accounts.addAccount(new SavingsAccount(
                new RegularCustomer("Michael Chen", "+1-555-0103",
                        "5 Birch Lane, Springfield", 41),
                15750.00, "Active"));

        accounts.addAccount(new CheckingAccount(
                new RegularCustomer("Emily Brown", "+1-555-0104",
                        "230 Cedar Ave, Springfield", 26),
                890.00, "Active"));

        accounts.addAccount(new SavingsAccount(
                new PremiumCustomer("David Wilson", "+1-555-0105",
                        "17 Oak Boulevard, Springfield", 52),
                25300.00, "Active"));
    }

    private static void printMenu() {
        System.out.println("""
            ╔════════════════════════════════════════════╗
            ║   BANK ACCOUNT MANAGEMENT - MAIN MENU      ║
            ╚════════════════════════════════════════════╝

            1. Create Account
            2. View Accounts
            3. Process Transaction
            4. View Transaction History
            5. Exit

            """);
    }

    private static void createAccount() {
        System.out.println("ACCOUNT CREATION");
        System.out.println("─".repeat(45));
        System.out.println();

        String name    = readString("Enter customer name: ");
        int age        = readInt("Enter customer age: ");
        String contact = readString("Enter customer contact: ");
        String address = readString("Enter customer address: ");

        System.out.println();
        System.out.println("Customer type:");
        System.out.println("1. Regular Customer (Standard banking services)");
        System.out.println("2. Premium Customer (Enhanced benefits, min balance $10,000)");
        System.out.println();
        int customerChoice = readInt("Select type (1-2): ");

        Customer customer;
        if (customerChoice == 2) {
            customer = new PremiumCustomer(name, contact, address, age);
        } else {
            customer = new RegularCustomer(name, contact, address, age);
        }

        System.out.println();
        System.out.println("Account type:");
        System.out.println("1. Savings Account (Interest: 3.5%, Min Balance: $500)");
        System.out.println("2. Checking Account (Overdraft: $1,000, Monthly Fee: $10)");
        System.out.println();
        int accountChoice = readInt("Select type (1-2): ");

        System.out.println();
        double deposit = readDouble("Enter initial deposit amount: $");
        if (deposit <= 0) { System.out.println("Deposit must be positive."); pressEnter(); return; }

        Account account;
        if (accountChoice == 1) {
            account = new SavingsAccount(customer, deposit, "Active");
        } else {
            account = new CheckingAccount(customer, deposit, "Active");
        }
        accounts.addAccount(account);

        System.out.println();
        System.out.println("✓ Account created successfully!");
        System.out.println("  Account Number: " + account.getAccountNumber());
        System.out.println("  Customer: " + customer.getName()
                + " (" + customer.getCustomerType() + ")"
                + " [ID: " + customer.getCustomerId() + "]");
        System.out.println("  Account Type: " + account.getAccountType());
        System.out.printf("  Initial Balance: $%,.2f%n", account.getBalance());
        System.out.println("  " + account.displayAccountDetails());
        System.out.println("  Status: " + account.getStatus());
        System.out.println();

        pressEnter();
    }

    private static void viewAccounts() {
        accounts.viewAllAccounts();
        System.out.println();
        pressEnter();
    }

    private static void processTransaction() {
        System.out.println("PROCESS TRANSACTION");
        System.out.println("─".repeat(45));
        System.out.println();

        String accNo = readString("Enter Account Number: ");
        Account account = accounts.findAccount(accNo);
        if (account == null) {
            System.out.println();
            System.out.println("Account not found: " + accNo);
            System.out.println();
            pressEnter();
            return;
        }

        System.out.println();
        System.out.println("Account Details:");
        System.out.println("Customer: " + account.getCustomer().getName());
        System.out.println("Account Type: " + account.getAccountType());
        System.out.printf("Current Balance: $%,.2f%n", account.getBalance());

        System.out.println();
        System.out.println("Transaction type:");
        System.out.println("1. Deposit");
        System.out.println("2. Withdrawal");
        System.out.println();

        int typeChoice = readInt("Select type (1-2): ");
        if (typeChoice != 1 && typeChoice != 2) {
            System.out.println();
            System.out.println("Invalid transaction type. Please enter 1 or 2.");
            System.out.println();
            pressEnter();
            return;
        }
        String type = (typeChoice == 1) ? "DEPOSIT" : "WITHDRAWAL";

        System.out.println();
        double amount = readDouble("Enter amount: $");
        if (amount <= 0) { System.out.println("\nAmount must be positive."); pressEnter(); return; }
        double previous = account.getBalance();
        double projected = type.equals("DEPOSIT") ? previous + amount : previous - amount;

        System.out.println();
        System.out.println("TRANSACTION CONFIRMATION");
        System.out.println("─".repeat(45));
        System.out.printf("Transaction ID: %s%n", Transaction.nextTransactionId());
        System.out.printf("Account: %s%n", account.getAccountNumber());
        System.out.printf("Type: %s%n", type);
        System.out.printf("Amount: $%,.2f%n", amount);
        System.out.printf("Previous Balance: $%,.2f%n", previous);
        System.out.printf("New Balance: $%,.2f%n", projected);
        System.out.println("─".repeat(45));
        System.out.println();

        if (!readYesNo("Confirm transaction? (Y/N): ")) {
            System.out.println();
            System.out.println("Transaction cancelled.");
            System.out.println();
            pressEnter();
            return;
        }

        boolean ok;
        try {
            ok = account.processTransaction(amount, type);
        } catch (RuntimeException e) {
            ok = false;
            System.out.println();
            System.out.println("✗ Transaction failed: " + e.getMessage());
            System.out.println();
            pressEnter();
            return;
        }

        if (!ok) {
            System.out.println();
            System.out.println("✗ Transaction failed. Check the amount and account limits.");
            System.out.println();
            pressEnter();
            return;
        }

        Transaction txn = new Transaction(
                account.getAccountNumber(),
                type,
                amount,
                account.getBalance());
        txns.addTransaction(txn);

        System.out.println();
        System.out.println("✓ Transaction completed successfully!");
        System.out.println();
        pressEnter();
    }

    private static void viewTransactionHistory() {
        System.out.println("VIEW TRANSACTION HISTORY");
        System.out.println("─".repeat(25));
        System.out.println();

        String accNo = readString("Enter Account Number: ");
        Account account = accounts.findAccount(accNo);
        if (account == null) {
            System.out.println();
            System.out.println("Account not found: " + accNo);
            System.out.println();
            pressEnter();
            return;
        }

        System.out.println();
        System.out.println("Account: " + account.getAccountNumber()
                + " - " + account.getCustomer().getName());
        System.out.println("Account Type: " + account.getAccountType());
        System.out.printf("Current Balance: $%,.2f%n", account.getBalance());
        System.out.println();

        txns.showAllTransactions(account.getAccountNumber());
        System.out.println();
        pressEnter();
    }
}