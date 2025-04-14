package com.cabin.leetcode;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DataUpdateConsumer {

    @KafkaListener(topics = "data-updates", groupId = "my-group")
    public void listen(String message) {
        System.out.println("Kafka Consumer Received: " + message);
    }
}
