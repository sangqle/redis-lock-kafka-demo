package com.cabin.leetcode.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerGroupDemo {
    public static final String TOPIC = "test-cabin-1";
    public static final String GROUP_ID = "my-group";

    // Listener này sẽ nhận các message từ topic "demo-topic" và thuộc group "my-group"
    @KafkaListener(topics = TOPIC, groupId =GROUP_ID, clientIdPrefix = "consumer-1")
    public void listen(ConsumerRecord<String, String> record) {
        String message = record.value();
        int partition = record.partition();
        long offset = record.offset();
        String threadName = Thread.currentThread().getName();

        System.err.println("Consumer 1: [" + threadName + "] Received message: '" + message +
                "' from partition: " + partition +
                ", offset: " + offset);
    }

    // Listener này sẽ nhận các message từ topic "demo-topic" và thuộc group "my-group"
    @KafkaListener(topics = TOPIC, groupId = GROUP_ID, clientIdPrefix = "consumer-2")
    public void listen2(ConsumerRecord<String, String> record) {
        String message = record.value();
        int partition = record.partition();
        long offset = record.offset();
        String threadName = Thread.currentThread().getName();

        System.err.println("Consumer 2: [" + threadName + "] Received message: '" + message +
                "' from partition: " + partition +
                ", offset: " + offset);
    }

    // Listener này sẽ nhận các message từ topic "demo-topic" và thuộc group "my-group"
    @KafkaListener(topics = TOPIC, groupId = GROUP_ID, clientIdPrefix = "consumer-3")
    public void listen3(ConsumerRecord<String, String> record) {
        String message = record.value();
        int partition = record.partition();
        long offset = record.offset();
        String threadName = Thread.currentThread().getName();

        System.err.println("Consumer 3: [" + threadName + "] Received message: '" + message +
                "' from partition: " + partition +
                ", offset: " + offset);
    }
}
