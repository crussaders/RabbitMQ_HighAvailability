package com.highAvailability.RabbitMQ.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receive(String message) {
        System.out.println("Received message: " + message);
    }
}