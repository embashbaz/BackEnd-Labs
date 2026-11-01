# Collections & Data Architecture

## Data structures
- Accounts: `HashMap<String, Account>` — O(1) lookup by account number
- Transactions: `ArrayList<Transaction>` — append-heavy, sorted on read

## Stream pipelines
- `AccountManager.getAllAccounts()` — sorted by account number
- `TransactionManager.calculateTotalDeposits()` — `filter(isDeposit().and(forAccount(...))).mapToDouble(Transaction::getAmount).sum()`
- `FilePersistenceService.save()` — `stream().map(this::formatAccount).toList()`

## Persistence format
- `data/accounts.txt`: `accNo;type;customerType;name;contact;address;age;balance;status;customerId`
- `data/transactions.txt`: `txnId;accNo;type;amount;balanceAfter;isoTimestamp`

## Counters
Restore constructors bypass auto-increment. `syncCounters()` after load sets
each counter to max-loaded index so new entities don't collide.

## Concurrency
- `Account.deposit/withdraw/getBalance` are `synchronized`.
- `AccountManager.transfer` is NOT synchronized across both accounts —
  multi-threaded transfers would need ordered locking. Documented as
  future work.