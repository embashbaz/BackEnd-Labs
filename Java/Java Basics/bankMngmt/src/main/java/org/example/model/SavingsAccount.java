package org.example.model;

public class SavingsAccount extends  Account{
    private  double interestRate;
    private  double minimumBalance;

    SavingsAccount(
    ){
         interestRate = 3.5;
         minimumBalance = 500;
    }

    @Override
    double withdraw(double amount) {
        if((getBalance() - amount) < minimumBalance){
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
        return "Savings";
    }

    double calculateInterest(){
        return this.getBalance() * (interestRate / 100);
    }
}
