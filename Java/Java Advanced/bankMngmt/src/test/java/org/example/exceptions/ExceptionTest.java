package org.example.exceptions;

import org.example.models.Account;
import org.example.models.Customer;
import org.example.models.Transaction;
import org.example.utils.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionTest {

    @BeforeEach
    void resetCounters() {
        Account.resetCounter();
        Customer.resetCounter();
        Transaction.resetCounter();
    }

    @Test
    void invalidAmountExceptionMessage(){
        String message = "Invalid amount";
        try {
            throw new InvalidAmountException(message);
        } catch (InvalidAmountException e){
            assertEquals(message, e.getMessage());
        }

    }

    @Test
    void  insufficientFundsExceptionMessage(){
        String message = "Insufficient funds";
        try {
            throw new InsufficientFundsException(message);
        } catch (InsufficientFundsException e){
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    void  overdraftExceededExceptionMessage(){
        String message = "Overdraft exceeded";
        try {
            throw new OverdraftExceededException(message);
        } catch (OverdraftExceededException e){
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    void  invalidAccountExceptionMessage(){
        String message = "Invalid account";
        try {
            throw new InvalidAccountException(message);
        } catch (InvalidAccountException e){
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    void  validatorThrowsOnZero(){
        assertThrows(InvalidAmountException.class, ()->{
            Validator.validateAmount(0);
        });
    }

    @Test
    void  validatorThrowsOnNegative(){
        assertThrows(InvalidAmountException.class, ()->{
            Validator.validateAmount(-45);
        });
    }

    @Test
    void  validatorAcceptsPositive(){
        Validator.validateAmount(45);
    }
}
