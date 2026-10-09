package org.example.model;

public class RegularCustomer extends Customer{

    public RegularCustomer(
            String name,String contact,String address, int age
    ){
        super(name, contact, address, age);
    }
    @Override
    public String displayCustomerDetails() {
        return "";
    }

    @Override
    public String getCustomerType() {
        return "Regular";
    }
}
