package org.example.config;

import org.example.constants.RedisQueueNames;
import org.example.subscriber.RedisSubscriber1;
import org.example.subscriber.RedisSubscriber2;
import org.example.subscriber.TradeMessageSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    public ChannelTopic topicMain() {
        return new ChannelTopic(RedisQueueNames.MAIN_CHANNEL);
    }

    @Bean
    public ChannelTopic topicRetry() {
        return new ChannelTopic(RedisQueueNames.RETRY_CHANNEL);
    }

    @Bean
    public ChannelTopic topicDLQ() {
        return new ChannelTopic(RedisQueueNames.DLQ_CHANNEL);
    }

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory factory,
                                                   MessageListenerAdapter listener1,
                                                   MessageListenerAdapter listener2,
                                                   MessageListenerAdapter tradeListener,
                                                   ChannelTopic topicMain,
                                                   ChannelTopic topicRetry) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        // listener1
        container.addMessageListener(listener1, topicMain);
        container.addMessageListener(listener1, topicRetry);
        // listener2
        container.addMessageListener(listener2, topicMain);
        container.addMessageListener(listener2, topicRetry);
        // trade listener
        container.addMessageListener(tradeListener, topicMain);
        container.addMessageListener(tradeListener, topicRetry);

        return container;
    }

    @Bean
    public MessageListenerAdapter listener1(RedisSubscriber1 subscriber1) {
        return new MessageListenerAdapter(subscriber1, "onMessage");
    }

    @Bean
    public MessageListenerAdapter listener2(RedisSubscriber2 subscriber2) {
        return new MessageListenerAdapter(subscriber2, "onMessage");
    }

    @Bean
    public MessageListenerAdapter tradeAdapter(TradeMessageSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "onMessage");
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        return template;
    }
}
