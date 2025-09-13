package com.lumen.subscription.service;

public class SnowflakeIdGenerator {

    private final long nodeId;
    private final static long EPOCH = 1609459200000L; // Jan 1, 2021
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeIdGenerator(long nodeId) {
        this.nodeId = nodeId;
    }

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & 4095; // 12-bit sequence
            if (sequence == 0) {
                while ((timestamp = System.currentTimeMillis()) <= lastTimestamp) {}
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;
        return ((timestamp - EPOCH) << 22) | (nodeId << 12) | sequence;
    }
}
