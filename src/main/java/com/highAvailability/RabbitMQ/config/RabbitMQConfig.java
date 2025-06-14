package com.highAvailability.RabbitMQ.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.queue}")
    private String queue;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    @Bean
    public Queue haQueue() {
        return QueueBuilder.durable(queue).build();
    }

    @Bean
    public DirectExchange haExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding binding(Queue haQueue, DirectExchange haExchange) {
        return BindingBuilder.bind(haQueue).to(haExchange).with(routingKey);
    }
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMissingQueuesFatal(false); // important for HA failover
        factory.setRecoveryInterval(5000L);   // 5 seconds retry
        return factory;
    }
}
