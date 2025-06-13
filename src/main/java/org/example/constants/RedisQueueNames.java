package org.example.constants;

public class RedisQueueNames {
    public static final String MAIN_CHANNEL = "trade-topic";
    public static final String RETRY_CHANNEL = "retry-topic";
    public static final String DLQ_CHANNEL   = "dlq-topic";
    public static final int MAX_RETRIES = 3;
}
