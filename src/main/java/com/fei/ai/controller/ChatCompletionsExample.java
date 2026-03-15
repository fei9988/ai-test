package com.fei.ai.controller;

import com.volcengine.ark.runtime.model.completion.chat.*;
import com.volcengine.ark.runtime.service.ArkService;
import java.util.ArrayList;
import java.util.List;

public class ChatCompletionsExample {
    public static void main(String[] args) {
        String apiKey = "3b1a7f6f-bc5c-421f-9a13-673d46dba170";
        // The base URL for model invocation
        ArkService service = ArkService.builder().apiKey(apiKey).baseUrl("https://ark-cn-beijing.bytedance.net/api/v3").build();
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content("请将下面内容进行结构化处理：火山方舟是火山引擎推出的大模型服务平台，提供模型训练、推理、评测、精调等全方位功能与服务，并重点支撑大模型生态。 火山方舟通过稳定可靠的安全互信方案，保障模型提供方的模型安全与模型使用者的信息安全，加速大模型能力渗透到千行百业，助力模型提供方和使用者实现商业新增长。").build();
        messages.add(userMessage);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
               .model("ep-20260309141223-lx88d")//Replace with your Endpoint ID
               .messages(messages)
               // .thinking(new ChatCompletionRequest.ChatCompletionRequestThinking("disabled")) // Manually disable deep thinking
               .build();
        service.createChatCompletion(chatCompletionRequest).getChoices().forEach(choice -> System.out.println(choice.getMessage().getContent()));
        // shutdown service
        service.shutdownExecutor();
    }
}
