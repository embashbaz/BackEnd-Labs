package org.example.model;

abstract public class Customer {
    private String customerId, name, contact, address;
    private int age;
    static int customerCounter = 0;

    public Customer(String name, String contact, String address, int age) {
        this.customerId = String.format("CUS%03d", ++customerCounter);
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.age = age;
    }

    public abstract String displayCustomerDetails();

    public abstract String getCustomerType();

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static int getCustomerCounter() {
        return customerCounter;
    }

    public static void setCustomerCounter(int customerCounter) {
        Customer.customerCounter = customerCounter;
    }
}
