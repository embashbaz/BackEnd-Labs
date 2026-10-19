package org.example.utils;

import org.example.exceptions.InvalidAmountException;

/**
 * Shared validation helpers used across the domain.
 *
 * <p>Centralises simple rules so that every caller enforces the same
 * contract (for example, {@link org.example.models.Account#deposit(double)}
 * and {@link org.example.services.AccountManager#transfer} both rely on
 * {@link #validateAmount(double)}).</p>
 */
public class Validator {

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
}