package com.highAvailability.RabbitMQ.controller;

import com.highAvailability.RabbitMQ.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageProducer producer;

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody String message) {
        producer.send(message);
        return ResponseEntity.ok("Sent: " + message);
    }
}
