package com.fei.ai.controller;


import com.fei.ai.service.ChatService;
import com.fei.ai.web.AuthContext;
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

    @Resource
    private ChatService chatService;


    @RequestMapping("/chat")
    public Flux<String> chat() {
        return chatClient.prompt()
                .user("你好，你是谁")
                .stream().content();
    }

    @RequestMapping("/chat/long")
    public Flux<String> longChat(AuthContext auth,
                                 @RequestParam String message,
                                 @RequestParam String chatId) {
        return chatService.longChat(auth, message, chatId);
    }



}
