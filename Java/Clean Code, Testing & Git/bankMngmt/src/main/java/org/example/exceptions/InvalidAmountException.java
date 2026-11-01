package org.example.exceptions;

public class InvalidAmountException extends RuntimeException{
    /**
     * InvalidAmountException invalid amounts like strings and negative numbers.
     *
     * @param message the Exception message
     */
    public InvalidAmountException(String message){super(message);}
}
