package com.vasisth.astrovasisth_core_svc.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vasisth.astrovasisth_core_svc.constants.Role;
import com.vasisth.astrovasisth_core_svc.dto.ChatHistoryReqRes;
import com.vasisth.astrovasisth_core_svc.entity.Message;
import com.vasisth.astrovasisth_core_svc.service.ChatHistoryService;
import com.vasisth.astrovasisth_core_svc.service.JwtService;
import com.vasisth.astrovasisth_core_svc.service.impl.ChatHistoryServiceFactory;
import com.vasisth.astrovasisth_core_svc.service.impl.ChatHistoryServiceImpl;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final JwtService jwtUtil;
    private final ChatHistoryService chatHistoryService;
    ObjectMapper om = new ObjectMapper();

    public ChatWebSocketHandler(JwtService jwtUtil,ChatHistoryService chatHistoryService) {
        this.chatHistoryService = chatHistoryService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        if (isAuthenticated(session)) {
            sessions.put(session.getId(), session);
        } else {
            closeSession(session);
        }
    }

    private boolean isAuthenticated(WebSocketSession session) {
        String token = extractToken(session);
        if (token == null) return false;
        Claims claims = jwtUtil.validateToken(token);
        return claims != null;
    }

    private String extractToken(WebSocketSession session) {
        String query = session.getUri().getQuery();
        if (query == null) return null;
        for (String pair : query.split("&")) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2 && "token".equals(keyValue[0])) {
                return keyValue[1];
            }
        }
        return null;
    }

    private void closeSession(WebSocketSession session) {
        try {
            session.close(CloseStatus.POLICY_VIOLATION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (!isAuthenticated(session)) {
            closeSession(session);
            return;
        }
        String payload = message.getPayload();
        Message commingMessage = om.readValue(payload, Message.class);
        sessions.forEach((id, ws) -> {
            if (session.getId().equals(id)) {
                try {
                    ChatHistoryReqRes chatHistoryReqRes = new ChatHistoryReqRes();
                    chatHistoryReqRes.setMessage(commingMessage.getMessage());
                    chatHistoryReqRes.setColleagueId(commingMessage.getColleagueId());
                    chatHistoryReqRes.setCustomerId(commingMessage.getCustomerId());
                    chatHistoryService.saveChatHistoryByCustomer(chatHistoryReqRes, Role.USER);
                    ws.sendMessage(new TextMessage(om.writeValueAsString(commingMessage)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session.getId());
    }
}