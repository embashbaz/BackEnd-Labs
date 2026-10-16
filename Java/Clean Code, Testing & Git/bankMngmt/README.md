# Bank Account Management System

A console-based Java application for managing bank accounts, customers, and
transactions. Built as the Week 2 lab for the "Clean Code, Testing & Git"
module — it extends the Week 1 foundation with refactored classes, custom
exceptions, JUnit 5 tests, and a GitHub Actions CI pipeline.

## Features

- **Manage Accounts** — create Savings or Checking accounts for Regular or Premium customers
- **View Accounts** — tabular list of all accounts with balances, status, and type-specific details
- **Perform Transactions** — deposit, withdraw, or transfer between accounts with a confirmation step
- **Generate Statements** — per-account ledger, newest-first, with totals and net change
- **Run Tests** — invoke the JUnit suite from the menu
- **Robust error handling** — custom exceptions with friendly console messages; the app never crashes on bad input

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
- **Gradle** (wrapper included — no local install required)
- A terminal that supports UTF-8 for the box-drawing characters and `✓` / `✗` marks

## Project layout

```
bankMngmt/
├── src/main/java/org/example/
│   ├── Main.java                       # Entry point, menu loop, handlers
│   ├── exceptions/
│   │   ├── InsufficientFundsException.java
│   │   ├── InvalidAccountException.java
│   │   ├── InvalidAmountException.java
│   │   ├── OverdraftExceededException.java
│   │   └── QuitApplicationException.java
│   ├── models/
│   │   ├── Account.java                # Abstract base for accounts
│   │   ├── SavingsAccount.java
│   │   ├── CheckingAccount.java
│   │   ├── Customer.java               # Abstract base for customers
│   │   ├── RegularCustomer.java
│   │   ├── PremiumCustomer.java
│   │   ├── Transactable.java           # Interface
│   │   └── Transaction.java
│   ├── services/
│   │   ├── AccountManager.java         # Composition: Account collection
│   │   ├── StatementGenerator.java     # Formats and prints statements
│   │   └── TransactionManager.java     # Composition: Transaction collection
│   └── utils/
│       ├── BoundedList.java            # ArrayList with a capacity cap
│       ├── InputParser.java            # Console input helpers
│       └── Validator.java              # Shared amount validation
├── src/test/java/org/example/
│   ├── exceptions/ExceptionTest.java
│   ├── models/AccountTest.java
│   ├── models/CheckingAccountTest.java
│   ├── models/SavingsAccountTest.java
│   ├── services/AccountManagerTest.java
│   └── services/TransactionManagerTest.java
├── docs/
│   └── git-workflow.md
└── .github/workflows/clean-code-ci.yml
```

## How to run

### From an IDE

1. Open the project as a **Gradle** Java project.
2. Confirm the project SDK is **Java 21**.
3. Run `org.example.Main`.

### From the command line

```bash
./gradlew run
```

The application seeds 5 demo accounts at startup (ACC001–ACC005) so you can
try View Accounts, Perform Transactions, and Generate Statements immediately.

## Running the tests

```bash
./gradlew test
```

Test reports are written to `build/reports/tests/test/index.html`.

You can also run the suite from the application menu: **Main Menu → 4. Run Tests**.
This invokes the JUnit Platform launcher directly and prints a live summary
to the console.

### Test results

The full suite runs on every push via GitHub Actions and locally on demand.
Latest run:

```
────────────────────────────────────────────────────────────

Test run finished after 61 ms
[         7 containers found      ]
[         0 containers skipped    ]
[         7 containers started    ]
[         0 containers aborted    ]
[         7 containers successful ]
[         0 containers failed     ]
[        49 tests found           ]
[         0 tests skipped         ]
[        49 tests started         ]
[         0 tests aborted         ]
[        49 tests successful      ]
[         0 tests failed          ]

────────────────────────────────────────────────────────────
✓ All 49 tests passed successfully!
```

**Summary:** 49 / 49 tests passing (100%), 0 failures, 0 skipped.

### Coverage by test class

| Test class | Focus |
|---|---|
| `AccountTest` | Deposit / withdraw balance updates, invalid amount handling, `processTransaction` dispatch, auto-generated account IDs |
| `SavingsAccountTest` | Minimum-balance rule, interest calculation |
| `CheckingAccountTest` | Overdraft within / at / beyond limit, monthly-fee waiver |
| `AccountManagerTest` | Account lookup, case-insensitive search, transfer, rollback on failure |
| `TransactionManagerTest` | Recording, filtering, newest-first ordering, totals, net change |
| `ExceptionTest` | Custom exception messages and `Validator` behaviour |

## Continuous Integration

GitHub Actions runs on every push to `dev` and on pull requests targeting
`main`. The workflow lives at `.github/workflows/clean-code-ci.yml` and:

1. Sets up JDK 21
2. Compiles main and test sources
3. Runs all JUnit 5 tests
4. Uploads the test report if anything fails
5. Packages the runnable JAR on success and uploads it as an artifact

Status badge:

![CI](https://github.com/embashbaz/BackEnd-Labs/actions/workflows/clean-code-ci.yml/badge.svg?branch=dev)

See [docs/git-workflow.md](docs/git-workflow.md) for branching rules,
commit conventions, cherry-pick usage, and CI details.

## Usage

Run the app, then pick from the menu:

```
╔════════════════════════════════════════════╗
║   BANK ACCOUNT MANAGEMENT - MAIN MENU      ║
╚════════════════════════════════════════════╝

1. Manage Accounts
2. Perform Transactions
3. Generate Account Statements
4. Run Tests
5. Exit
```

### Example: deposit with confirmation

```
Enter choice: 2
Enter option: 1

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

### Example: transfer between accounts

```
Enter choice: 2
Enter option: 2

Enter Account Number to get money from: ACC001
Enter Account Number to transfer money to: ACC002
Enter amount: $250

Confirm transaction? (Y/N): Y

✓ Transaction completed successfully!
```

Two transactions are recorded — a `WITHDRAWAL` on the source and a
`DEPOSIT` on the destination.

### Example: statement with error handling

```
Enter choice: 3
Enter Account Number: ACC001

Account: John Smith (Savings)
Current Balance: $6,750.00

TXN ID   | DATE/TIME         | TYPE      | AMOUNT     | BALANCE
────────────────────────────────────────────────────────────────
TXN002   | 06-06-2025 10:24  | DEPOSIT   | +$1,500.00 | $6,750.00
TXN001   | 06-06-2025 10:20  | WITHDRAWAL| -$750.00   | $5,250.00
────────────────────────────────────────────────────────────────
Total Transactions: 2
Total Deposits:     $1,500.00
Total Withdrawals:  $750.00
Net Change:         +$750.00
```

### Example: run tests from the menu

```
Enter choice: 4

Running tests with JUnit...
────────────────────────────────────────────────────────────
[        49 tests successful      ]
[         0 tests failed          ]
────────────────────────────────────────────────────────────
✓ All 49 tests passed successfully!
```

## Design notes

- **Encapsulation** — all model fields are `private` with public getters/setters.
- **Inheritance** — `Account` → `SavingsAccount` / `CheckingAccount`; `Customer` → `RegularCustomer` / `PremiumCustomer`.
- **Abstraction** — base classes are `abstract` and declare `displayAccountDetails()` / `getAccountType()` and `displayCustomerDetails()` / `getCustomerType()`.
- **Interface** — `Transactable` declares `processTransaction(double, String)`; `Account` implements it and delegates to the overridden `withdraw`, so subclass balance rules are enforced.
- **Polymorphism** — `withdraw` is overridden in `SavingsAccount` (minimum balance) and `CheckingAccount` (overdraft limit).
- **Composition** — `AccountManager` owns a `BoundedList<Account>` (size 50); `TransactionManager` owns a `BoundedList<Transaction>` (size 200).
- **Separation of concerns** — `TransactionManager` only stores, filters, and aggregates. `StatementGenerator` handles all printing.
- **Custom exceptions** — `InvalidAmountException`, `InsufficientFundsException`, `OverdraftExceededException`, `InvalidAccountException` — all `RuntimeException` subclasses with clear messages.
- **Static counters** — `Account.accountCounter`, `Customer.customerCounter`, `Transaction.transactionCounter` generate the `ACC%03d`, `CUS%03d`, and `TXN%03d` IDs.

## Validation and limits

- Menu choices, amounts, and Y/N prompts loop until valid input is given.
- Deposits and withdrawals must be strictly positive (`Validator.validateAmount`).
- Savings withdrawals are rejected if they would take the balance below $500.
- Checking withdrawals are rejected if they would take the balance below −$1,000.
- Transfers validate both accounts, the amount, and the source-side balance rules before moving funds; a failed deposit rolls back the source.

## Known limitations

- In-memory only — data is not persisted between runs.
- `BoundedList` throws `IllegalStateException` if the account (50) or transaction (200) cap is exceeded.
- Monthly fee application and interest calculation are implemented on the account classes but not exposed via the menu (outside the required user stories).

## Git workflow

Summary — full details in [docs/git-workflow.md](docs/git-workflow.md).

- `main` — stable, release-ready
- `dev` — integration branch for this lab
- `feature/<topic>` — short-lived, one feature per branch

Conventional commit style: `type(scope): message` where `type` ∈
{`feat`, `fix`, `refactor`, `test`, `docs`, `ci`, `chore`}.

Cherry-pick is used to move individual tested commits between feature
branches. See the workflow doc for a worked example.

## Author

- **Emery Bashige** — emery.bashige@amalitech.com

## License

This project was created for academic purposes.