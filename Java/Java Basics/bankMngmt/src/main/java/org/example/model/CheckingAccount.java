package org.example.model;

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
        if(getCustomer() instanceof PremiumCustomer){
            monthlyFee = 0;
        } else {
            monthlyFee = 10;
        }
    }


    @Override
    public double withdraw(double amount) {
        if((getBalance() - amount) < (0 - overdraftLimit)){
            throw new RuntimeException("Withdrawing this amount will ...");
        }
        return  super.withdraw(amount);
    }

    @Override
    public String displayAccountDetails() {
         return """
                
                """;
    }

    @Override
   public String getAccountType() {
        return "Checking";
    }

    public void applyMonthlyFee(){
        setBalance(getBalance() - monthlyFee);
    }
}
