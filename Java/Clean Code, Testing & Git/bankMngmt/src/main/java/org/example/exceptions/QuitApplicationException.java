package org.example.exceptions;

public class QuitApplicationException extends RuntimeException{
    /**
     * QuitApplicationException for the application main menu, we throw this to tell the main application that the user want to quit the application.
     *
     */
    public QuitApplicationException(){super();}
}
