package org.example.utils;

import org.example.models.Transaction;

import java.util.Comparator;
import java.util.function.Predicate;

public final class FunctionalUtils {
    private FunctionalUtils() {}

    public static Predicate<Transaction> isDeposit()  { return t -> t.getType().equals("DEPOSIT"); }
    public static Predicate<Transaction> isWithdrawal() { return t -> t.getType().equals("WITHDRAWAL"); }

    public static Comparator<Transaction> byAmountDescending() {
        return Comparator.comparingDouble(Transaction::getAmount).reversed();
    }
    public static Comparator<Transaction> byDateDescending() {
        return Comparator.comparing(Transaction::getDateTime).reversed();
    }

    public static Predicate<Transaction> forAccount(String accountNumber) {
        return t -> t.getAccountNumber().equalsIgnoreCase(accountNumber);
    }

}
