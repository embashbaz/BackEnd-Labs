package org.example.utils;

import org.example.exceptions.InvalidAmountException;

import java.util.regex.Pattern;

/**
 * Shared validation helpers used across the domain.
 *
 * <p>Centralises simple rules so that every caller enforces the same
 * contract (for example, {@link org.example.models.Account#deposit(double)}
 * and {@link org.example.services.AccountManager#transfer} both rely on
 * {@link #validateAmount(double)}).</p>
 */
public class ValidationUtils {

    private static final Pattern ACCOUNT_NUMBER = Pattern.compile("ACC\\d{3}");
    private static final Pattern EMAIL =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE =
            Pattern.compile("^\\+?\\d{1,3}-\\d{3}-\\d{4}$");

    /**
     * Ensures the given amount is strictly positive.
     *
     * @param amount the amount to validate
     * @throws InvalidAmountException if {@code amount <= 0}
     */
    public static void validateAmount(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Amount cannot be equal or less than 0");
        }
    }

    public static boolean isValidAccountNumber(String s) {
        return ACCOUNT_NUMBER.matcher(s).matches();
    }
    public static boolean isValidEmail(String s) {
        return EMAIL.matcher(s).matches();
    }
    public static boolean isValidPhone(String s) {
        return PHONE.matcher(s).matches();
    }

    public static boolean contactPredicate(String c) {
        return isValidEmail(c) || isValidPhone(c);
    }

}