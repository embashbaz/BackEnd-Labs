# Bank Account Management System — Week 3

A console-based Java application that manages bank accounts, customers, and
transactions. This is the Week 3 (Bank Account III) extension of the Week 2
lab, adding Collections, Functional Programming, File I/O, Regex validation,
and Concurrency.

## Features

### Week 2 (retained)
- Manage accounts (Savings / Checking, Regular / Premium customers)
- Perform deposits, withdrawals, and account-to-account transfers
- Generate account statements with running balance
- Custom exception hierarchy with friendly console error messages

### Week 3 (new)
- **Collections** — `HashMap<String, Account>` for O(1) lookups, `ArrayList<Transaction>` for append-heavy history
- **Functional Programming** — Streams, Lambdas, Method References, reusable `Predicate`/`Comparator` instances in `FunctionalUtils`
- **File Persistence** — NIO `Files`/`Paths` auto-save to `data/accounts.txt` and `data/transactions.txt` on exit; auto-load on startup
- **Regex Validation** — account numbers `ACC\d{3}`, emails, and phone numbers validated in `ValidationUtils`
- **Concurrency** — `synchronized` balance mutations, multi-thread simulation, parallel-stream batch simulation

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

- Java 21 (uses `List.reversed()` and text blocks)
- Gradle (wrapper included)
- A UTF-8 terminal for the box-drawing characters and ✓ / ✗ marks

## Project layout

```
bankMngmt/
├── src/main/java/org/example/
│   ├── Main.java
│   ├── exceptions/
│   │   ├── InsufficientFundsException.java
│   │   ├── InvalidAccountException.java
│   │   ├── InvalidAmountException.java
│   │   ├── OverdraftExceededException.java
│   │   └── QuitApplicationException.java
│   ├── models/
│   │   ├── Account.java
│   │   ├── SavingsAccount.java
│   │   ├── CheckingAccount.java
│   │   ├── Customer.java
│   │   ├── RegularCustomer.java
│   │   ├── PremiumCustomer.java
│   │   ├── Transaction.java
│   │   └── Transactable.java
│   ├── services/
│   │   ├── AccountManager.java
│   │   ├── TransactionManager.java
│   │   ├── FilePersistenceService.java
│   │   └── StatementGenerator.java
│   └── utils/
│       ├── InputParser.java
│       ├── ValidationUtils.java
│       ├── FunctionalUtils.java
│       └── ConcurrencyUtils.java
├── src/test/java/org/example/
├── data/
│   ├── accounts.txt
│   └── transactions.txt
├── docs/
│   └── collections-architecture.md
└── README.md
```

## How to run

```bash
./gradlew run
```

First run seeds 5 demo accounts (ACC001–ACC005). Data auto-saves on exit
and auto-loads on next start.

## Main Menu

```
1. Manage Accounts
2. Perform Transactions
3. Generate Account Statements
4. Save / Load data
5. Run Concurrent Simulation
6. Run Tests
7. Exit
```

## Week 3 feature details

### Collections & Functional Programming

- Accounts held in `HashMap<String, Account>`; `findAccount()` is O(1).
- Statement sorting uses `FunctionalUtils.byDateDescending()`.
- Aggregate queries use stream pipelines with composed predicates:

```java
transactions.stream()
    .filter(FunctionalUtils.isDeposit().and(FunctionalUtils.forAccount(accountNumber)))
    .mapToDouble(Transaction::getAmount)
    .sum();
```

### File I/O (NIO)

- `data/accounts.txt` — semicolon-delimited, one account per line
- `data/transactions.txt` — semicolon-delimited, ISO-8601 timestamps
- Load uses `Files.lines()` + method references (`map(this::parseAccount)`)
- Corrupt lines are skipped with a warning; startup never crashes
- Static counters (`ACC`, `CUS`, `TXN`) are synced to max-loaded on startup
  so new entities never collide with restored ones

### Regex Validation

- `ACC\d{3}` — account numbers
- `^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$` — email
- `^\+?\d{1,3}-\d{3}-\d{4}$` — phone
- Centralised in `ValidationUtils`

### Concurrency

- `Account.deposit` / `withdraw` / `getBalance` are `synchronized`
- Menu option 5 runs a parallel-stream batch of 10 deposits plus 3 concurrent
  threads (2 deposits + 1 withdrawal) on the same account, then prints the
  final balance

## Sample session

```
╔════════════════════════════════════════════╗
║   BANK ACCOUNT MANAGEMENT - MAIN MENU      ║
╚════════════════════════════════════════════╝

1. Manage Accounts
2. Perform Transactions
3. Generate Account Statements
4. Save / Load data
5. Run Concurrent Simulation
6. Run Tests
7. Exit

Enter choice: 5

Running batch simulation on ACC001 with balance $5,250.00
Running multi-thread simulation on ACC001
Thread-1: Depositing $500.00 to ACC001
Thread-2: Depositing $300.00 to ACC001
Thread-3: Withdrawing $200.00 from ACC001

✓ Thread-safe operations completed successfully.
Final Balance for ACC001: $10,850.00
```

## Tests

```bash
./gradlew test
```

Coverage:
- `AccountTest` — deposit / withdraw / processTransaction dispatch
- `SavingsAccountTest` — minimum balance, interest
- `CheckingAccountTest` — overdraft limits, fee waiver
- `AccountManagerTest` — lookup, transfer, rollback
- `TransactionManagerTest` — aggregates, ordering
- `ExceptionTest` — exception messages, regex validation

You can also run the suite from the app menu: **Main Menu → 6. Run Tests**.

## Known limitations

- Files are rewritten wholesale on save; fine for a lab, would use upserts in a real DB
- `AccountManager.transfer()` is not synchronized across two accounts — a
  multi-threaded transfer would need ordered locking to prevent deadlock;
  documented in `docs/collections-architecture.md`
- `BoundedList` (Week 2) is retained as a utility but no longer used by the managers

## Git workflow

Conventional commit style: `type(scope): message` where `type` ∈
{`feat`, `fix`, `refactor`, `test`, `docs`, `ci`, `chore`}.

## Author

- Emery Bashige — emery.bashige@amalitech.com