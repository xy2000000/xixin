package com.fxy.xixin.ai.service;

import reactor.core.publisher.Flux;

public interface ChatService {

    String chat(String message, String chatId);

    Flux<String> stream(String message, String chatId);
}