package org.example.controller;

import org.example.dto.TradeMessage;
import org.example.publisher.TradePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    private TradePublisher publisher;

    @PostMapping("/send")
    public String sendTrade(@RequestBody TradeMessage message) {
        publisher.publishToMain(message);
        return "Message sent to main queue!";
    }
}
