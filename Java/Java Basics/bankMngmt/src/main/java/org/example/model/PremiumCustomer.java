package org.example.model;

public class PremiumCustomer extends  Customer{

    private  double minimumBalance = 10000;

    public PremiumCustomer(
            String name, String contact, String address, int age
    ){
        super(name, contact, address, age);
    }

    boolean hasWaivedFees(){
        return true;
    }

    @Override
    public String displayCustomerDetails() {
        return "";
    }

    @Override
    public String getCustomerType() {
        return "Premium";
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(double minimumBalance) {
        this.minimumBalance = minimumBalance;
    }
}
