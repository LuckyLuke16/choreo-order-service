package com.example.orderservice.exception;

public class PaymentNotUniqueException extends RuntimeException {
    public PaymentNotUniqueException(long paymentId) {
        super("PaymentId is already used");
    }
}
