package com.example.orderservice.service;

import com.example.orderservice.model.order.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class AmqpConsumer {

    private final OrderService orderService;

    Logger logger = LoggerFactory.getLogger(AmqpConsumer.class);

    public AmqpConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = "#{saveOrderQueue.name}")
    @SendTo("#{exchange.name}/order.saved")
    public Message<OrderDTO> processOrder(Message<OrderDTO> orderDetailsMessage) {
        String userId = orderDetailsMessage.getHeaders().get("userId", String.class);
        String correlationId = orderDetailsMessage.getHeaders().get("correlationId", String.class);
        OrderDTO orderDetails = orderDetailsMessage.getPayload();
        Long paymentId = orderDetails.getPaymentId();

        try {
            String orderId = correlationId;
            this.orderService.saveOrder(userId, paymentId, orderId);

            return MessageBuilder
                    .withPayload(orderDetails)
                    .setHeader("userId", userId)
                    .setHeader("correlationId", correlationId)
                    .build();

        } catch(Exception e) {
            logger.warn("Order could not be saved: correlation id: {}, user id: {}", correlationId, userId, e);
            throw new AmqpRejectAndDontRequeueException("order saving failed");
        }
    }
}
