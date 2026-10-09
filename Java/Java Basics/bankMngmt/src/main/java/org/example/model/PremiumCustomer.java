package org.example.model;

public class PremiumCustomer extends  Customer{

    private  int minimumBalance = 10000;

    PremiumCustomer(
            String name,String contact,String address, int age
    ){
        super.setAddress(address);
        super.setName(name);
        super.setContact(contact);
        super.setAge(age);
    }

    boolean hasWaivedFees(){
        return true;
    }

    @Override
    String displayCustomerDetails() {
        return "";
    }

    @Override
    String getCustomerType() {
        return "Premium";
    }

    public int getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(int minimumBalance) {
        this.minimumBalance = minimumBalance;
    }
}
