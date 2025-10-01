package com.example.adapter;

// Client class that uses the PaymentGateway interface
public class PaymentClient {
    private PaymentGateway paymentGateway;

    public PaymentClient(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public void makePayment(double amount) {
        System.out.println("Client: Initiating payment for $" + amount);
        paymentGateway.processPayment(amount);
    }
}
