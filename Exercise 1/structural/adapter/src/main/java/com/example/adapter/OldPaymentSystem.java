package com.example.adapter;

// Adaptee class with an incompatible interface
public class OldPaymentSystem {
    public void makePayment(double amountInDollars) {
        System.out.println("Old Payment System: Processing payment of $" + amountInDollars);
        // Simulate some legacy payment processing logic
    }
}
