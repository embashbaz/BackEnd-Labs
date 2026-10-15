package org.example.exceptions;

public class OverdraftExceededException extends RuntimeException{
    public OverdraftExceededException(String message){super(message);}
}
