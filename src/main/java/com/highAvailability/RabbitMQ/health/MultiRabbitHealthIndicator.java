package com.highAvailability.RabbitMQ.health;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MultiRabbitHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        Health.Builder status = Health.up();

        checkNode(status, "localhost", 5672);
        checkNode(status, "localhost", 5673);

        return status.build();
    }

    private void checkNode(Health.Builder builder, String host, int port) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername("guest");
        factory.setPassword("guest");

        try (com.rabbitmq.client.Connection conn = factory.newConnection()) {
            builder.withDetail(host + ":" + port, "UP");
        } catch (Exception ex) {
            builder.withDetail(host + ":" + port, "DOWN (" + ex.getMessage() + ")");
        }
    }
}