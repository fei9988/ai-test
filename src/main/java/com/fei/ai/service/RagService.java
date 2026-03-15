//package com.fei.ai.service;
//
//import org.springframework.ai.document.Document;
//import org.springframework.ai.vectorstore.VectorStore;
//import org.springframework.ai.vectorstore.SearchRequest;
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Flux;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class RagService {
//
//    private final VectorStore vectorStore;
//    private final ChatClient chatClient;
//
//    // 构造时注入 VectorStore 和 ChatClient
//    public RagService(VectorStore vectorStore, ChatClient.Builder chatClientBuilder) {
//        this.vectorStore = vectorStore;
//        this.chatClient = chatClientBuilder.build();
//    }
//
//    /**
//     * 第一步：模拟导入知识
//     */
//    public void importKnowledge() {
//        List<Document> documents = List.of(
//            new Document("公司规定：每周五下午 4 点后可以提前下班，无需打卡。", Map.of("category", "hr")),
//            new Document("办公区禁止吸烟，违者罚款 200 元。", Map.of("category", "rule"))
//        );
//        // Spring AI 会自动帮你把这些文字转成向量存入 Milvus
//        vectorStore.add(documents);
//        System.out.println("✅ 知识库导入成功！");
//    }
//
//    /**
//     * 第二步：基于知识库回答
//     */
//    public Flux<String> askQuestion(String query) {
//        // 1. 从 Milvus 检索相关片段 (取最接近的 2 条)
//        List<Document> similarDocs = vectorStore.similaritySearch(
//                SearchRequest.builder().query(query).build()
//        );
//
//        // 2. 提取内容拼成上下文
//        String context = similarDocs.stream()
//                .map(Document::getFormattedContent)
//                .collect(Collectors.joining("\n"));
//
//        // 3. 构建 Prompt 并调用大模型
//        return chatClient.prompt()
//                .system("你是一个专业的公司行政助理。请根据以下已知信息回答问题。如果你不知道答案，请诚实告知。")
//                .user(u -> u.text("""
//                    已知信息：
//                    {context}
//
//                    待回答的问题：
//                    {query}
//                    """)
//                    .param("context", context)
//                    .param("query", query))
//                .stream()
//                .content();
//    }
//}