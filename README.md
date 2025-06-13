# Project Title
redis-pub-sub-spring-boot

	•	Send and receive JSON objects via Redis Pub/Sub.
	•	Use Jackson for serialization/deserialization.
	•	Keep multiple subscribers handling the same structured message.

## API Reference

```http
curl -X POST http://localhost:8080/trade/send \
 -H "Content-Type: application/json" \
 -d '{"tradeId": "TX100", "action": "BUY", "amount": 1000}'
```


```http
curl -X POST http://localhost:8080/trade/send \
 -H "Content-Type: application/json" \
 -d '{"tradeId": "TX_FAIL", "action": "SELL", "amount": 500}'
```
