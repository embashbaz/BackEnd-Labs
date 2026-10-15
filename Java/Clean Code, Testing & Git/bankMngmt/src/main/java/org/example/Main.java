package org.example;

import org.example.exceptions.*;
import org.example.services.AccountManager;
import org.example.services.StatementGenerator;
import org.example.services.TransactionManager;
import org.example.models.*;

import static org.example.utils.InputParser.*;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final AccountManager accountManager = new AccountManager();
    private static final TransactionManager transactionManager = new TransactionManager();
    private static final StatementGenerator statementGenerator = new StatementGenerator(transactionManager);

    public static void main(String[] args) {
        seedAccounts();
        boolean running = true;
        while (running) {
            printMenu();
            try {
                int choice = readInt("Enter choice: ");
                switch (choice) {
                    case 1 -> manageAccount();
                    case 2 -> transact();
                    case 3 -> viewTransactionHistory();
                    case 4 -> runTest();
                    case 5 -> running = false;
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (QuitApplicationException e){
                running = false;
            }

        }
        System.out.println("Thank you for using Bank Account Management System!");
        System.out.println("Goodbye!");
    }

    private static void seedAccounts() {
        accountManager.addAccount(new SavingsAccount(
                new RegularCustomer("John Smith", "+1-555-0101",
                        "12 Elm Street, Springfield", 34),
                5250.00, "Active"));

        accountManager.addAccount(new CheckingAccount(
                new RegularCustomer("Sarah Johnson", "+1-555-0102",
                        "88 Pine Road, Springfield", 29),
                3450.00, "Active"));

        accountManager.addAccount(new SavingsAccount(
                new RegularCustomer("Michael Chen", "+1-555-0103",
                        "5 Birch Lane, Springfield", 41),
                15750.00, "Active"));

        accountManager.addAccount(new CheckingAccount(
                new RegularCustomer("Emily Brown", "+1-555-0104",
                        "230 Cedar Ave, Springfield", 26),
                890.00, "Active"));

        accountManager.addAccount(new SavingsAccount(
                new PremiumCustomer("David Wilson", "+1-555-0105",
                        "17 Oak Boulevard, Springfield", 52),
                25300.00, "Active"));
    }

    private static void manageAccount(){
        System.out.println("""
            
            ACCOUNT MANAGEMENT
           
            1. Create Account
            2. View Accounts
            3. Exit

            """);
        int choice        = readInt("Enter option: ");
        switch (choice){
            case 1 -> createAccount();
            case 2 -> viewAccounts();
            case 3 -> {
                pressEnter();
            }
            default -> {
                System.out.println("Invalid choice. Try again.");
                manageAccount();
                return;
            }
        }
    }

    private static void transact(){
        System.out.println("""
            
            TRANSACT
           
            1. Withdraw or deposit
            2. Account to Account transfer
            3. Exit

            """);
        int choice        = readInt("Enter option: ");
        switch (choice){
            case 1 -> processTransaction();
            case 2 -> processTransferTransaction();
            case 3 -> {
                pressEnter();
            }
            default -> {
                System.out.println("Invalid choice. Try again.");
                transact();
                return;
            }
        }
    }


    private static void runTest(){

    }



    private static void printMenu() {
        System.out.println("""
            ╔════════════════════════════════════════════╗
            ║   BANK ACCOUNT MANAGEMENT - MAIN MENU      ║
            ╚════════════════════════════════════════════╝

            1. Manage Accounts
            2. Perform Transactions
            3. Generate Account Statements
            4. Run Tests
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

        int customerChoice = getCustomerType();
        Customer customer;
        if (customerChoice == 2) {
            customer = new PremiumCustomer(name, contact, address, age);
        } else if(customerChoice == 1) {
            customer = new RegularCustomer(name, contact, address, age);
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        int accountChoice = getAccountType();

        System.out.println();
        double deposit = readPositiveAmount();

        Account account;
        if (accountChoice == 1) {
            account = new SavingsAccount(customer, deposit, "Active");
        } else if (accountChoice == 2){
            account = new CheckingAccount(customer, deposit, "Active");
        }else {
            System.out.println("Invalid choice.");
            pressEnter();
            return;
        }
        accountManager.addAccount(account);

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

    private static double readPositiveAmount(){
        double deposit = readDouble("Enter amount: $");
        if (deposit <= 0) {
            System.out.println("Amount must be positive.\n");  return readPositiveAmount(); }
        else {
            return deposit;
        }
    }

    private static int getAccountType(){
        System.out.println();
        System.out.println("Account type:");
        System.out.println("1. Savings Account (Interest: 3.5%, Min Balance: $500)");
        System.out.println("2. Checking Account (Overdraft: $1,000, Monthly Fee: $10)");
        System.out.println();
        int accountChoice = readInt("Select type (1-2): ");
        if(accountChoice > 2 || accountChoice < 1){
            System.out.println("Invalid input. Please enter 1 or 2.");
            return getAccountType();
        }  else {
            return accountChoice;
        }
    }

    private static int getCustomerType(){
        System.out.println();
        System.out.println("Customer type:");
        System.out.println("1. Regular Customer (Standard banking services)");
        System.out.println("2. Premium Customer (Enhanced benefits, min balance $10,000)");
        System.out.println();
        int customerChoice = readInt("Select type (1-2): ");

        if(customerChoice > 2 || customerChoice < 1){
            System.out.println("Invalid input. Please enter a 1 or 2.");
            return getCustomerType();
        }  else {
            return customerChoice;
        }


    }

    private static void viewAccounts() {
        accountManager.viewAllAccounts();
        System.out.println();
        pressEnter();
    }

    private static void processTransaction() {
        System.out.println("PROCESS TRANSACTION");
        System.out.println("─".repeat(45));
        System.out.println();


        Account account = null;
        while (account == null) {
            String accNo = readString("Enter Account Number: ");
            if (accNo.equalsIgnoreCase("quit")) return;
            try {
                account = accountManager.findAccount(accNo);
            } catch (InvalidAccountException e) {
                System.out.println("Account not found: " + accNo);
            }
        }

        System.out.println();
        System.out.println("Account Details:");
        System.out.println("Customer: " + account.getCustomer().getName());
        System.out.println("Account Type: " + account.getAccountType());
        System.out.printf("Current Balance: $%,.2f%n", account.getBalance());


        int typeChoice = validateTransactionType();

        String type = (typeChoice == 1) ? "DEPOSIT" : "WITHDRAWAL";

        System.out.println();
        double amount = readPositiveAmount();
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

        boolean ok = false;
        try {
            account.processTransaction(amount, type);
            ok = true;
        } catch (IllegalArgumentException e) {
            System.out.println();
            System.out.println("✗ Transaction failed: " + e.getMessage());
            System.out.println();
            pressEnter();
            return;
        } catch (InvalidAmountException e) {
            System.out.println("✗ Invalid amount: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println("✗ Transaction failed: " + e.getMessage());
        } catch (OverdraftExceededException e) {
            System.out.println("✗ Transaction failed: " + e.getMessage());
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
        transactionManager.addTransaction(txn);

        System.out.println();
        System.out.println("✓ Transaction completed successfully!");
        System.out.println();
        pressEnter();
    }

    private static void processTransferTransaction() {
        System.out.println("PROCESS TRANSACTION");
        System.out.println("─".repeat(45));
        System.out.println();


        String toAccNo = readString("Enter Account Number to transfer money to: ");


        Account fromAccount = null;
        while (fromAccount == null) {
            String accNo = readString("Enter Account Number to get money from: ");
            if (accNo.equalsIgnoreCase("quit")) return;
            try {
                fromAccount = accountManager.findAccount(accNo);
            } catch (InvalidAccountException e) {
                System.out.println("Account not found: " + accNo);
            }
        }

        Account toAccount = null;
        while (toAccount == null) {
            String accNo = readString("Enter Account Number to transfer money to: ");
            if (accNo.equalsIgnoreCase("quit")) return;
            try {
                toAccount = accountManager.findAccount(accNo);
            } catch (InvalidAccountException e) {
                System.out.println("Account not found: " + accNo);
            }
        }


        System.out.println();
        System.out.println("From Account Details:");
        System.out.println("Customer: " + fromAccount.getCustomer().getName());
        System.out.println("Account Type: " + fromAccount.getAccountType());
        System.out.printf("Current Balance: $%,.2f%n", fromAccount.getBalance());

        System.out.println();
        System.out.println("To Account Details:");
        System.out.println("Customer: " + toAccount.getCustomer().getName());
        System.out.println("Account Type: " + toAccount.getAccountType());

        System.out.println();
        double amount = readPositiveAmount();

        System.out.println();
        System.out.println("TRANSACTION CONFIRMATION");
        System.out.println("─".repeat(45));
        System.out.printf("Amount: $%,.2f%n", amount);
        System.out.println("─".repeat(45));
        System.out.println();

        if (!readYesNo("Confirm transaction? (Y/N): ")) {
            System.out.println();
            System.out.println("Transaction cancelled.");
            System.out.println();
            pressEnter();
            return;
        }

        boolean ok = false;
        try {
            accountManager.transfer(fromAccount.getAccountNumber(), toAccount.getAccountNumber(), amount);
            ok = true;
        } catch (IllegalArgumentException e) {
            System.out.println();
            System.out.println("✗ Transaction failed: " + e.getMessage());
            System.out.println();
            pressEnter();
            return;
        } catch (InvalidAmountException e) {
            System.out.println("✗ Invalid amount: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println("✗ Transaction failed: " + e.getMessage());
        } catch (OverdraftExceededException e) {
            System.out.println("✗ Transaction failed: " + e.getMessage());
        }

        if (!ok) {
            System.out.println();
            System.out.println("✗ Transaction failed. Check the amount and account limits.");
            System.out.println();
            pressEnter();
            return;
        }

        Transaction fromTxn = new Transaction(
                fromAccount.getAccountNumber(),
                "WITHDRAWAL",
                amount,
                fromAccount.getBalance());
        transactionManager.addTransaction(fromTxn);

        Transaction toTxn = new Transaction(
                toAccount.getAccountNumber(),
                "DEPOSIT",
                amount,
                toAccount.getBalance());
        transactionManager.addTransaction(toTxn);

        System.out.println();
        System.out.println("✓ Transaction completed successfully!");
        System.out.println();
        pressEnter();
    }

    private static int validateTransactionType(){
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
            return validateTransactionType();
        }
        return typeChoice;
    }

    private static void viewTransactionHistory() {
        System.out.println("VIEW TRANSACTION HISTORY");
        System.out.println("─".repeat(25));
        System.out.println();

        String accNo = readString("Enter Account Number: ");
        Account account;
        try {
             account = accountManager.findAccount(accNo);
        } catch (InvalidAccountException e){
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

        statementGenerator.generateStatement(account);
        System.out.println();
        pressEnter();
    }
}