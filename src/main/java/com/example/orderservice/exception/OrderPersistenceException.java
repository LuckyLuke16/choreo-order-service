package com.example.orderservice.exception;

public class OrderPersistenceException extends RuntimeException {
    public OrderPersistenceException(String userId, long paymentId) {
        super("Order from user:" + userId + " with payment id:" + paymentId + "could not be saved");
    }
}
