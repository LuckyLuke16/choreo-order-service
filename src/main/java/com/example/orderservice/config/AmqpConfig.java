package com.example.orderservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    static final String topicExchangeName = "choreography-exchange";

    static final String saveOrderQueue = "save-order-queue";

    @Bean
    Queue saveOrderQueue() {
        return new Queue(saveOrderQueue, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding saveOrderBinding(Queue saveOrderQueue, TopicExchange exchange) {
        return BindingBuilder.bind(saveOrderQueue).to(exchange).with("payment.succeeded");
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
