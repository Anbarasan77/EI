package com.example.adapter;

// Another concrete implementation of the Target interface
public class ModernPaymentGateway implements PaymentGateway {
    @Override
    public void processPayment(double amount) {
        System.out.println("Modern Payment Gateway: Processing payment of $" + amount);
        // Simulate modern payment processing logic
    }
}
