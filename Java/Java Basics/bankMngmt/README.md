# Bank Account Management System

A console-based Java application for managing bank accounts, customers, and
transactions. Built as a lab project to demonstrate object-oriented design
principles, abstraction, inheritance, polymorphism, composition, and
fundamental data-structures concepts in Java.

## Features

- **Create Account** — register a new customer with a Savings or Checking account
- **View Accounts** — list all accounts with balances, status, and type-specific details
- **Process Transaction** — deposit or withdraw with a confirmation step
- **View Transaction History** — per-account ledger in reverse chronological order with summary totals
- **Menu Navigation** — looped console menu with input validation

### Account types

| Type | Interest | Minimum Balance | Overdraft | Monthly Fee |
|---|---|---|---|---|
| Savings | 3.5% annually | $500 | — | — |
| Checking | — | — | $1,000 | $10 (waived for Premium customers) |

### Customer types

| Type | Benefits |
|---|---|
| Regular | Standard banking services |
| Premium | Minimum balance $10,000, waived monthly fees, priority service |

## Requirements

- **Java 21** (uses `List.reversed()` and text blocks)
- A terminal that supports UTF-8 (for box-drawing characters and the `✓`/`✗` marks)

## Project layout

```
src/main/java/org/example/
├── Main.java                     # Entry point, menu loop, handlers
├── domain/
│   ├── AccountManager.java       # Composition: manages Account collection
│   └── TransactionManager.java   # Composition: manages Transaction collection
├── model/
│   ├── Account.java              # Abstract base for accounts
│   ├── SavingsAccount.java
│   ├── CheckingAccount.java
│   ├── Customer.java             # Abstract base for customers
│   ├── RegularCustomer.java
│   ├── PremiumCustomer.java
│   ├── Transactable.java         # Interface: processTransaction(amount, type)
│   └── Transaction.java          # One recorded deposit or withdrawal
└── utils/
    ├── BoundedList.java          # ArrayList with a capacity cap
    └── InputParser.java          # Console input helpers
```

## How to run

### From an IDE (IntelliJ, Eclipse, VS Code)

1. Open the project as a Gradle Java project.
2. Ensure the project SDK is **Java 21**.
3. Run `org.example.Main`.

### From the command line

```bash
# compile
javac -d out $(find src/main/java -name "*.java")

# run
java -cp out org.example.Main
```

The application seeds 5 demo accounts at startup (ACC001–ACC005) so you can
immediately try View Accounts, Process Transaction, and View Transaction
History without creating anything first.

## Usage

Run the application, then pick from the menu:

```
╔════════════════════════════════════════════╗
║   BANK ACCOUNT MANAGEMENT - MAIN MENU      ║
╚════════════════════════════════════════════╝

1. Create Account
2. View Accounts
3. Process Transaction
4. View Transaction History
5. Exit
```

### Example: process a deposit

```
Enter choice: 3

PROCESS TRANSACTION
─────────────────────────────────────────────

Enter Account Number: ACC001

Account Details:
Customer: John Smith
Account Type: Savings
Current Balance: $5,250.00

Transaction type:
1. Deposit
2. Withdrawal

Select type (1-2): 1

Enter amount: $1500

TRANSACTION CONFIRMATION
─────────────────────────────────────────────
Transaction ID: TXN001
Account: ACC001
Type: DEPOSIT
Amount: $1,500.00
Previous Balance: $5,250.00
New Balance: $6,750.00
─────────────────────────────────────────────

Confirm transaction? (Y/N): Y

✓ Transaction completed successfully!
```

## Design notes

- **Encapsulation** — All model fields are `private` with public getters/setters.
- **Inheritance** — `Account` → `SavingsAccount` / `CheckingAccount`; `Customer` → `RegularCustomer` / `PremiumCustomer`.
- **Abstraction** — Both base classes are `abstract` and declare `displayAccountDetails()` / `getAccountType()` and `displayCustomerDetails()` / `getCustomerType()` respectively.
- **Interface** — `Transactable` declares `processTransaction(double amount, String type)`; `Account` implements it and delegates to the overridden `withdraw` so the balance rules in each subclass are enforced.
- **Polymorphism** — `withdraw` is overridden in `SavingsAccount` (minimum balance) and `CheckingAccount` (overdraft limit). `displayAccountDetails` returns type-specific text.
- **Composition** — `AccountManager` owns a `BoundedList<Account>` (size 50); `TransactionManager` owns a `BoundedList<Transaction>` (size 200). Neither inherits from a collection.
- **Static counters** — `Account.accountCounter`, `Customer.customerCounter`, `Transaction.transactionCounter` generate the `ACC%03d`, `CUS%03d`, and `TXN%03d` IDs.

## Data structures

- `BoundedList<E>` extends `ArrayList<E>` and overrides `add` to enforce a capacity.
- Account lookup uses a **linear search** over the list (`stream().filter(...).findFirst()`), which is O(n) — fine for the 50-account cap required by the lab.
- Transaction history is filtered by account number and **reversed** so the newest entry appears first.
- Summary totals iterate once per account, each O(n) over the transaction list.

## Validation and limits

- Menu choices, amounts, and Y/N prompts loop until valid input is given.
- Deposits and withdrawals must be strictly positive.
- Savings withdrawals are rejected if they would take the balance below $500.
- Checking withdrawals are rejected if they would take the balance below −$1,000.
- Premium customers have their Checking monthly fee waived ($0.00).

## Known limitations
- Once you start an action like withdrawing, there is no way to cancel it, you have to go until the end
- Data is in-memory only; nothing is persisted between runs.
- `BoundedList` throws `IllegalStateException` if the account (50) or transaction (200) cap is exceeded.
- Monthly fee application and interest calculation are implemented on the account classes but are not exposed via the menu (they are not part of the required user stories).

## Author

- **Emery Bashige** — emery.bashige@amalitech.com

## License

This project was created for academic purposes.