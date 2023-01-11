package com.example.orderservice.service;

import com.example.orderservice.exception.OrderPersistenceException;
import com.example.orderservice.exception.PaymentNotUniqueException;
import com.example.orderservice.model.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String saveOrder(String userId, long paymentId, String orderId) {
        try {
            if(!this.isPaymentIdUnique(paymentId))
                throw new PaymentNotUniqueException(paymentId);

            Order orderToSave = new Order(orderId, paymentId, userId);
            this.orderRepository.save(orderToSave);
            logger.info("Order with id: {} was saved", orderToSave.getOrderId());

            return orderToSave.getOrderId();
        } catch (Exception e) {
            logger.warn("Order from user {} could not be saved", userId);

            throw new OrderPersistenceException(userId, paymentId);
        }
    }

    private boolean isPaymentIdUnique(long paymentId) {
        return this.orderRepository.findByPaymentId(paymentId).isEmpty();
    }
}
