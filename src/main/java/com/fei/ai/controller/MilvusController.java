//package com.fei.ai.controller;
//
//
//import com.fei.ai.service.RagService;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Flux;
//
//import javax.annotation.Resource;
//
///**
// * @author bytedance
// * @date 2026/3/4
// **/
//@RestController
//@RequestMapping("/milvus")
//public class MilvusController {
//
//    @Resource
//    private RagService ragService;
//
//
//    @RequestMapping("/chat")
//    public Flux<String> chat() {
////        ragService.importKnowledge();
//        return ragService.askQuestion("周五几点下班？");
//    }
//
//}
