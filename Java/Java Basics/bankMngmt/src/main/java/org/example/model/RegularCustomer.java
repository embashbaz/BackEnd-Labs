package org.example.model;

public class RegularCustomer extends Customer{

    RegularCustomer(
            String name,String contact,String address, int age
    ){
        super.setAddress(address);
        super.setName(name);
        super.setContact(contact);
        super.setAge(age);
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
