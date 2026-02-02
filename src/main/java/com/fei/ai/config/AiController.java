package com.fei.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ai_1")
public class AiController {

    private final ChatClient chatClient;

    // 构造器注入 Builder，构建默认 Client
    public AiController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
        // 最简单的调用：输入字符串，返回字符串
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
    
    // 进阶：使用 Prompt Template (提示词模板)
    @GetMapping("/joke")
    public String joke(@RequestParam String topic) {
        return chatClient.prompt()
                .user(u -> u.text("给我讲一个关于 {topic} 的笑话")
                            .param("topic", topic))
                .call()
                .content();
    }
    
    // 进阶：直接返回 Java Bean (结构化输出)
    @GetMapping("/analyze")
    public AnalysisResult analyze(@RequestParam String text) {
        return chatClient.prompt()
                .user(text)
                .call()
                .entity(AnalysisResult.class); // 自动将 JSON 转为 Java 对象
    }
}

// 简单的记录类
record AnalysisResult(String sentiment, List<String> keywords) {}