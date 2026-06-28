package com.fxy.xixin.ai.controller;

import com.fxy.xixin.ai.service.ChatService;
import com.fxy.xixin.common.result.R;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/chat")
    public R<Map<String, String>> chat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        String chatId = request.getOrDefault("chatId", "default");
        String answer = chatService.chat(message, chatId);
        return R.ok(Map.of("answer", answer, "chatId", chatId));
    }

    @PostMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        String chatId = request.getOrDefault("chatId", "default");
        return chatService.stream(message, chatId);
    }



}