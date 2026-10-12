package org.example.model;

public interface Transactable {
    boolean processTransaction(double amount, String type);
}

