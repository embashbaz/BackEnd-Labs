package org.example.model;

public class CheckingAccount extends  Account{
    private double overdraftLimit = 1000;
    private float monthlyFee = 10;


    CheckingAccount(){
        overdraftLimit = 1000;
        if(getCustomer() instanceof PremiumCustomer){
            monthlyFee = 0;
        } else {
            monthlyFee = 10;
        }
    }


    @Override
    double withdraw(double amount) {
        if((getBalance() - amount) < (0 - overdraftLimit)){
            throw new RuntimeException("Withdrawing this amount will ...");
        }
        return  super.withdraw(amount);
    }

    @Override
    String displayAccountDetails() {
         return """
                
                """;
    }

    @Override
    String getAccountType() {
        return "Checking";
    }

    void applyMonthlyFee(){
        setBalance(getBalance() - monthlyFee);
    }
}
