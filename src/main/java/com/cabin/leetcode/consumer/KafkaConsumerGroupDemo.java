package com.cabin.leetcode.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerGroupDemo {

    // Listener này sẽ nhận các message từ topic "demo-topic" và thuộc group "my-group"
    @KafkaListener(topics = "demo-topic", groupId = "my-group")
    public void listen(ConsumerRecord<String, String> record) {
        String message = record.value();
        int partition = record.partition();
        long offset = record.offset();
        String threadName = Thread.currentThread().getName();

        System.out.println("[" + threadName + "] Received message: '" + message +
                "' from partition: " + partition +
                ", offset: " + offset);
    }
}
