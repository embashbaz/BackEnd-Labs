package org.example.exceptions;

public class InsufficientFundsException extends RuntimeException{
    /**
     * InsufficientFundsException for when an account doesn't have enough money to withdraw from.
     *
     * @param message the Exception message
     */
    public InsufficientFundsException(String message){super(message);}
}
