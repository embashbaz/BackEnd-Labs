package org.example.model;

abstract public class Account {


    private String accountNumber;
    private Customer customer;
    private double balance;
    private String status;


    double deposit(double amount){
        return amount;
    }

    double withdraw(double amount){
        return amount;
    }


    abstract String displayAccountDetails();

    abstract String getAccountType();

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

