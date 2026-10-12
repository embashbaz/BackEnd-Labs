package org.example.model;

import java.util.Objects;

abstract public class Account implements Transactable{


    private String accountNumber;
    private Customer customer;
    private double balance;
    private String status;
    static int accountCounter = 0;
    Account(
            Customer customer,
            double balance,
            String status
    ){
        this.accountNumber = String.format("ACC%03d", ++accountCounter);
        this.customer = customer;
        this.balance = balance;
        this.status = status;
    }

    Account(){}


    void createAccount(){

    }

    new Account();

    public double deposit(double amount){
        balance = balance + amount;
        return amount;
    }

    public double withdraw(double amount){
        balance = balance - amount;
        return amount;
    }


    public abstract String displayAccountDetails();

    public abstract String getAccountType();

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

    @Override
    public boolean processTransaction(double amount, String type) {
        if(amount <= 0){
            return false;
        }

            if (Objects.equals(type, "WITHDRAWAL")) {
                withdraw(amount);
            } else if (Objects.equals(type, "DEPOSIT")) {
                deposit(amount);
            } else  {
                return false;
            }

        return true;
    }
}

