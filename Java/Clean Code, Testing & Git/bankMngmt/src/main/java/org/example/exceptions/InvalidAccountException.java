package org.example.exceptions;

public class InvalidAccountException  extends RuntimeException{
    /**
     * InvalidAccountException for invalid accounts.
     *
     * @param message the Exception message
     */
    public InvalidAccountException(String message){super(message);}
}
