package org.example.controller;

import org.example.publisher.RedisPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pubsub")
public class PubSubController {

    @Autowired
    private RedisPublisher publisher;

    @PostMapping("/publish")
    public String publish(@RequestParam String message) {
        publisher.publishToMain(message);
        return "Message published: " + message;
    }
}
