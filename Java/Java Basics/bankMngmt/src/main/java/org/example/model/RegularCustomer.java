package org.example.model;

public class RegularCustomer extends Customer{

    public RegularCustomer(
            String name,String contact,String address, int age
    ){
        super(name, contact, address, age);
    }
    @Override
    public String displayCustomerDetails() {
        return String.format("""
            Customer ID: %s
            Name:        %s
            Age:         %d
            Contact:     %s
            Address:     %s
            Type:        %s""",
                getCustomerId(),
                getName(),
                getAge(),
                getContact(),
                getAddress(),
                getCustomerType());
    }

    @Override
    public String getCustomerType() {
        return "Regular";
    }
}
