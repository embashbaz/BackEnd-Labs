package org.example.models;

/**
 * Contract for objects that can process a deposit or withdrawal by type.
 *
 * <p>Implemented by {@link Account} so that a caller (such as the console
 * UI) can drive any account type generically without knowing whether it is
 * a savings or checking account.</p>
 */
public interface Transactable {

    /**
     * Processes a transaction of the given type.
     *
     * @param amount the transaction amount; must be strictly positive
     * @param type   the transaction type — {@code "DEPOSIT"} or
     *               {@code "WITHDRAWAL"}
     * @throws org.example.exceptions.InvalidAmountException if {@code amount <= 0}
     * @throws IllegalArgumentException if {@code type} is not a recognised type
     */
    void processTransaction(double amount, String type);
}