package com.vasisth.astrovasisth_core_svc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vasisth.astrovasisth_core_svc.constants.Role;
import com.vasisth.astrovasisth_core_svc.dto.ChatHistoryReqRes;

import java.util.List;

public interface ChatHistoryService {
    List<ChatHistoryReqRes> getChaHistoryByUserId(String userId);

    ChatHistoryReqRes getChatsById(String customerId, String colleagueId) throws JsonProcessingException;

    public ChatHistoryReqRes saveChatHistoryByCustomer(ChatHistoryReqRes chatHistoryReqRes, Role role) throws JsonProcessingException;
}
