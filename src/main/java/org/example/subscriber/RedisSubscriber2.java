package org.example.subscriber;

import org.springframework.stereotype.Service;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

@Service
public class RedisSubscriber2 implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("[Subscriber 2] Received: " + message.toString());
    }
}
