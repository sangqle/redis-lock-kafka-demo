package com.cabin.leetcode;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class DataService {

    private final RedissonClient redissonClient;
    private final KafkaTemplate<String, String> kafkaTemplate;

    // Assume this is your shared data store (could be a DB in real scenarios)
    private int sharedData = 0;
    private static final String LOCK_KEY = "data_lock";
    private static final String TOPIC = "data-updates";

    @Autowired
    public DataService(RedissonClient redissonClient, KafkaTemplate<String, String> kafkaTemplate) {
        this.redissonClient = redissonClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void updateSharedData(String requestId, int delta) {
        RLock lock = redissonClient.getLock(LOCK_KEY);
        // Try to acquire the lock with a maximum wait time and a lease time
        boolean acquired = false;
        try {
            acquired = lock.tryLock(100, 5000, TimeUnit.MILLISECONDS);
            if (acquired) {
                // Simulate data modification logic
                System.out.println("[" + requestId + "] Acquired lock, current data: " + sharedData);
                sharedData += delta;
                System.out.println("[" + requestId + "] Updated shared data to: " + sharedData);

                // Publish an event message to Kafka indicating data update
                CompletableFuture<SendResult<String, String>> send = kafkaTemplate.send(TOPIC, "Data updated by " + requestId + ", new value: " + sharedData);
                send.whenComplete((result, ex) -> {
                    if (ex != null) {
                        System.err.println("[" + requestId + "] Failed to send message: " + ex.getMessage());
                    } else {
                        System.out.println("[" + requestId + "] Message sent successfully: " + result.getProducerRecord().value());
                    }
                });

            } else {
                System.err.println("[" + requestId + "] Could not acquire lock, try again later.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (acquired && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    // For testing purposes, a getter to view the shared data value
    public int getSharedData() {
        return sharedData;
    }
}
