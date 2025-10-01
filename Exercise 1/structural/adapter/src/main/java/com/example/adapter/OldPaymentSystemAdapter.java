package com.example.adapter;

// Adapter class
public class OldPaymentSystemAdapter implements PaymentGateway {
    private OldPaymentSystem oldPaymentSystem;

    public OldPaymentSystemAdapter(OldPaymentSystem oldPaymentSystem) {
        this.oldPaymentSystem = oldPaymentSystem;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Adapter: Converting modern payment request to old system format.");
        oldPaymentSystem.makePayment(amount); // Adapting the call
    }
}
