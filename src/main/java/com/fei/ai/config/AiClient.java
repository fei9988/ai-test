package com.fei.ai.config;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bytedance
 * @date 2026/1/31
 **/
@Configuration
public class AiClient {

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder().build();
    }

    @Bean
    public ChatClient chatClient(OpenAiChatModel openAiChatModel, ChatMemory chatMemory) {
        // 设置 utf-8 编码
        return ChatClient.builder(openAiChatModel)
                .defaultSystem("你叫小段段，你是一个专业的聊天机器人，称呼我为主人")
                .defaultAdvisors(new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }


}
