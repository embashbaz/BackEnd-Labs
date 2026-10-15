package org.example.models;

public class PremiumCustomer extends  Customer{

    private  double minimumBalance = 10000;

    public PremiumCustomer(
            String name, String contact, String address, int age
    ){
        super(name, contact, address, age);
    }

    public boolean hasWaivedFees(){
        return true;
    }

    @Override
    public String displayCustomerDetails() {
        return String.format("""
            Customer ID:     %s
            Name:            %s
            Age:             %d
            Contact:         %s
            Address:         %s
            Type:            %s
            Minimum Balance: $%,.2f
            Fee Waiver:      Yes""",
                getCustomerId(),
                getName(),
                getAge(),
                getContact(),
                getAddress(),
                getCustomerType(),
                minimumBalance);
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
