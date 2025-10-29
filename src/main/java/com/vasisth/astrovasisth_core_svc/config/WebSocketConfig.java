package com.vasisth.astrovasisth_core_svc.config;

import com.vasisth.astrovasisth_core_svc.filters.ChatWebSocketHandler;
import com.vasisth.astrovasisth_core_svc.service.ChatHistoryService;
import com.vasisth.astrovasisth_core_svc.service.JwtService;
import com.vasisth.astrovasisth_core_svc.service.impl.ChatHistoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ChatHistoryService chatHistoryService;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(chatHistoryService), "/chat")
                .setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler webSocketHandler(ChatHistoryService chatHistoryService) {
        return new ChatWebSocketHandler(jwtService,chatHistoryService);
    }
}