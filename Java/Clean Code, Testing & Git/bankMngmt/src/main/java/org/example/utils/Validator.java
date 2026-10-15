package org.example.utils;

import org.example.exceptions.InvalidAmountException;

public class Validator
{

    public static void validateAmount(double amount){
        if(amount <= 0){
            throw new InvalidAmountException("Amount cannot be equal or less than 0");
        }
    }
}
