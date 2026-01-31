package com.fei.aitest.controller;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author bytedance
 * @date 2026/1/26
 **/
@RestController
@RequestMapping("/api/ai")
public class AiController {

    // 建议从环境变量获取，这里为了演示直接使用你 Main.java 中的 Key
    private static final String API_KEY = "sk-80e4e6e0b5f747f5af996c811d6c6a58";

    /**
     * 连续对话接口
     * 客户端需要维持并发送之前的对话历史
     */
    @PostMapping("/chat")
    public List<Map<String, String>> chat(@RequestBody List<Map<String, String>> history) throws NoApiKeyException, InputRequiredException {
        // 1. 将前端传来的历史记录转换为 SDK 需要的 Message 对象列表
        List<Message> messages = new ArrayList<>();
        for (Map<String, String> msg : history) {
            messages.add(Message.builder()
                    .role(msg.get("role"))
                    .content(msg.get("content"))
                    .build());
        }

        // 2. 调用 DashScope SDK
        Generation gen = new Generation();
        GenerationParam param = GenerationParam.builder()
                .apiKey(API_KEY)
                .model("deepseek-v3")
                .resultFormat("message")
                .messages(messages)
                .build();

        GenerationResult result = gen.call(param);
        
        // 3. 获取 AI 的回复并转换回简单 Map 格式返回
        Message assistantMsg = result.getOutput().getChoices().get(0).getMessage();
        
        List<Map<String, String>> updatedHistory = new ArrayList<>(history);
        updatedHistory.add(Map.of(
                "role", assistantMsg.getRole(),
                "content", assistantMsg.getContent()
        ));

        return updatedHistory;
    }
}
