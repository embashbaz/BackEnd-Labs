package org.example.model;

public class RegularCustomer extends Customer{

    public RegularCustomer(
            String name,String contact,String address, int age
    ){
        super(name, contact, address, age);
    }
    @Override
    String displayCustomerDetails() {
        return "";
    }

    @Override
    String getCustomerType() {
        return "Regular";
    }
}
