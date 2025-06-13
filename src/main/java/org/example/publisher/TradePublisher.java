package org.example.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.constants.RedisQueueNames;
import org.example.dto.TradeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TradePublisher {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired private ObjectMapper objectMapper;

    public void publish(TradeMessage message, String channel) {
        try {
            String json = objectMapper.writeValueAsString(message);
            redisTemplate.convertAndSend(channel, json);
            System.out.println("Published to " + channel + ": " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publishToMain(TradeMessage message) {
        publish(message, RedisQueueNames.MAIN_CHANNEL);
    }

    public void publishToRetry(TradeMessage message) {
        publish(message, RedisQueueNames.RETRY_CHANNEL);
    }

    public void publishToDLQ(TradeMessage message) {
        publish(message, RedisQueueNames.DLQ_CHANNEL);
    }
}
