package com.fei.ai.controller;


import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class ChatController {

    @Resource
    private ChatClient chatClient;


    @RequestMapping("/chat")
    public Flux<String> chat() {
        return chatClient.prompt()
                .user("你好，你是谁")
                .stream().content();
    }

    @RequestMapping("/chat/long")
    public Flux<String> longChat(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .stream().content();
    }

}
