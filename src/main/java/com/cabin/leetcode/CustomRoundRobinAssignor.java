package com.cabin.leetcode;

import org.apache.kafka.clients.consumer.RoundRobinAssignor;

public class CustomRoundRobinAssignor extends RoundRobinAssignor {

    @Override
    public String name() {
        return "RoundRobinAssigner";
    }
}