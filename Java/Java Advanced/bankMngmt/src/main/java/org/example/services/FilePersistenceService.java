package org.example.services;

import org.example.models.*;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class FilePersistenceService {
    private static final Path ACCOUNTS = Path.of("data/accounts.txt");
    private static final Path TXNS     = Path.of("data/transactions.txt");


    public boolean accountHasData() throws IOException {
        return Files.exists(ACCOUNTS) && Files.exists(TXNS) && Files.size(ACCOUNTS) > 0;
    }

    public void save(AccountManager am, TransactionManager tm) throws IOException {
        Files.createDirectories(ACCOUNTS.getParent());
        List<String> accLines = am.getAllAccounts().stream()
                .map(this::formatAccount)          // method ref → rubric
                .toList();
        Files.write(ACCOUNTS, accLines);

        List<String> txnLines = tm.getAllTransactions().stream()
                .map(this::formatTransaction)
                .toList();
        Files.write(TXNS, txnLines);
    }

    public void load(AccountManager am, TransactionManager tm) throws IOException {
        if (!Files.exists(ACCOUNTS)) { Files.createDirectories(ACCOUNTS.getParent()); Files.createFile(ACCOUNTS); }
        if (!Files.exists(TXNS))     { Files.createFile(TXNS); }

        try (Stream<String> lines = Files.lines(ACCOUNTS)) {
            lines.filter(l -> !l.isBlank() && !l.startsWith("#"))
                    .forEach(l -> {
                        try {
                            am.addAccount(parseAccount(l));
                        } catch (Exception e) {
                            System.err.println("Skipping bad account line: " + l);
                        }
                    });
        }

        try (Stream<String> lines = Files.lines(TXNS)) {
            lines.filter(l -> !l.isBlank() && !l.startsWith("#"))
                    .forEach(t ->{
                                try {
                                    tm.addTransaction(parseTransaction(t));
                                } catch (Exception e) {
                                    System.err.println("Skipping bad transaction line: " + t);
                                }
                            });
        }

        syncCounters(am, tm);
    }

    private void syncCounters(AccountManager am, TransactionManager tm) {
        int maxAcc = am.getAllAccounts().stream()
                .mapToInt(a -> Integer.parseInt(a.getAccountNumber().substring(3)))
                .max().orElse(0);
        Account.setAccountCounter(maxAcc);

        int maxCus = am.getAllAccounts().stream()
                .mapToInt(a -> Integer.parseInt(a.getCustomer().getCustomerId().substring(3)))
                .max().orElse(0);
        Customer.setCustomerCounter(maxCus);

        int maxTxn = tm.getAllTransactions().stream()
                .mapToInt(t -> Integer.parseInt(t.getTransactionID().substring(3)))
                .max().orElse(0);
        Transaction.setTransactionCounter(maxTxn);
    }

    private String formatAccount(Account a) {
        return String.join(";",
                a.getAccountNumber(),
                a.getAccountType(),                        // "Savings" / "Checking"
                a.getCustomer().getCustomerType(),         // "Regular" / "Premium"
                a.getCustomer().getName(),
                a.getCustomer().getContact(),
                a.getCustomer().getAddress(),
                String.valueOf(a.getCustomer().getAge()),
                String.format("%.2f", a.getBalance()),
                a.getStatus(),
                a.getCustomer().getCustomerId());
    }

    private Account parseAccount(String line) {
        String[] p = line.split(";", -1);
        if(p.length != 10){
            System.err.println("Skipping bad account line: " + line);
            throw new RuntimeException();
        }
        String accNo = p[0], accType = p[1], custType = p[2];
        String name = p[3], contact = p[4], address = p[5];
        int age = Integer.parseInt(p[6]);
        double balance = Double.parseDouble(p[7]);
        String status = p[8], custId = p[9];

        Customer c = custType.equals("Premium")
                ? new PremiumCustomer(custId, name, contact, address, age)
                : new RegularCustomer(custId, name, contact, address, age);

        return accType.equals("Savings")
                ? new SavingsAccount(accNo, c, balance, status)
                : new CheckingAccount(accNo, c, balance, status);
    }

    private String formatTransaction(Transaction t) {
        return String.join(";",
                t.getTransactionID(),
                t.getAccountNumber(),
                t.getType(),
                String.format("%.2f", t.getAmount()),
                String.format("%.2f", t.getBalanceAfter()),
                t.getDateTime().toString());                // ISO-8601
    }

    private Transaction parseTransaction(String line) {
        String[] p = line.split(";", -1);
        if(p.length != 6){
            System.err.println("Skipping bad transaction line: " + line);
            throw new RuntimeException();
        }
        return new Transaction(p[0], p[1], p[2],
                Double.parseDouble(p[3]), Double.parseDouble(p[4]),
                LocalDateTime.parse(p[5]));
    }
}