package com.cabin.leetcode.controllers;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class KafkaProducerController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    // The topic to send messages to
    private final String TOPIC = "demo-topic";

    public KafkaProducerController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // HTTP POST endpoint for publishing messages to Kafka
    @PostMapping("/publish")
    public String sendMessage(@RequestParam("message") String message) {
        // Sends a message to the Kafka topic "demo-topic"
        kafkaTemplate.send(TOPIC, message);
        return "Message sent to Kafka topic: " + message;
    }
}
