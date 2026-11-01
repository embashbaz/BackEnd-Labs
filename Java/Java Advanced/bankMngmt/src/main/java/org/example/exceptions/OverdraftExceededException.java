package org.example.exceptions;

public class OverdraftExceededException extends RuntimeException{
    /**
     * OverdraftExceededException for when a user tries to withdraw more than they can.
     *
     * @param message the Exception message
     */
    public OverdraftExceededException(String message){super(message);}
}
