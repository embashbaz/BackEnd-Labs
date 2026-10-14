package org.example.models;

public class SavingsAccount extends  Account{
    private  double interestRate;
    private  double minimumBalance;

    public SavingsAccount(
            Customer customer,
            double balance,
            String status
    ){
        super(customer, balance, status);

         interestRate = 3.5;
         minimumBalance = 500;
    }

    @Override
    public double withdraw(double amount) {
        if((getBalance() - amount) < minimumBalance){
            throw new RuntimeException("You cannot withdraw beyond the allowed limit");
        }
        return  super.withdraw(amount);
    }

    @Override
    public String displayAccountDetails() {
        return "Interest Rate: 3.5% | Min Balance: $500.00";
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    public double calculateInterest(){
        return this.getBalance() * (interestRate / 100);
    }
}
