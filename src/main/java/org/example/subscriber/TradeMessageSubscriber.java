package org.example.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.constants.RedisQueueNames;
import org.example.dto.TradeMessage;
import org.example.publisher.RedisPublisher;
import org.example.publisher.TradePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class TradeMessageSubscriber implements MessageListener {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired private TradePublisher publisher;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String json = new String(message.getBody());
            TradeMessage trade = objectMapper.readValue(json, TradeMessage.class);

            // Simulate error if tradeId ends with "FAIL"
            if (trade.getTradeId().endsWith("FAIL")) {
                throw new RuntimeException("Simulated Failure");
            }

            System.out.println("✅ Processed: " + trade);

        } catch (Exception ex) {
            try {
                TradeMessage trade = objectMapper.readValue(message.getBody(), TradeMessage.class);
                trade.incrementRetry();

                if (trade.getRetryCount() <= RedisQueueNames.MAX_RETRIES) {
                    System.out.println("🔁 Retry #" + trade.getRetryCount() + " for " + trade.getTradeId());
                    publisher.publishToRetry(trade);
                } else {
                    System.out.println("☠️ Moved to DLQ: " + trade);
                    publisher.publishToDLQ(trade);
                }

            } catch (Exception e) {
                System.out.println("⚠️ Failed to move message to retry/DLQ");
                e.printStackTrace();
            }
        }
    }
}
