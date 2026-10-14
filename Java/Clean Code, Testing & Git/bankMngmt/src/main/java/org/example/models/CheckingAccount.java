package org.example.models;

import org.example.exceptions.OverdraftExceededException;

public class CheckingAccount extends  Account{
    private double overdraftLimit ;
    private double monthlyFee;


    public CheckingAccount(
            Customer customer,
            double balance,
            String status
    ){
        super(customer, balance, status);
        overdraftLimit = 1000;
        if (getCustomer() instanceof PremiumCustomer pc && pc.hasWaivedFees()) {
            monthlyFee = 0;
        } else {
            monthlyFee = 10;
        }
    }


    @Override
    public double withdraw(double amount) {
        if((getBalance() - amount) < (0 - overdraftLimit)){
            throw new OverdraftExceededException("You can not withdraw beyond the allowed limit");
        }
        return  super.withdraw(amount);
    }

    @Override
    public String displayAccountDetails() {
        String fee = (monthlyFee == 0)
                ? "$0.00 (WAIVED - Premium Customer)"
                : String.format("$%.2f", monthlyFee);
        return String.format("Overdraft Limit: $%,.2f | Monthly Fee: %s", overdraftLimit, fee);
    }

    @Override
   public String getAccountType() {
        return "Checking";
    }

    public void applyMonthlyFee(){
        setBalance(getBalance() - monthlyFee);
    }
}
