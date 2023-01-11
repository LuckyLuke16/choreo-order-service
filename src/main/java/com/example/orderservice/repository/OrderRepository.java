package com.example.orderservice.repository;

import com.example.orderservice.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByPaymentId(long PaymentId);
}
