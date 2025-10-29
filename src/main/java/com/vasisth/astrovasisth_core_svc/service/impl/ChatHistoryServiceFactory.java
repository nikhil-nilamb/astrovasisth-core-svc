package com.vasisth.astrovasisth_core_svc.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vasisth.astrovasisth_core_svc.constants.Role;
import com.vasisth.astrovasisth_core_svc.dto.ChatHistoryReqRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatHistoryServiceFactory {

    @Autowired
    private static  ChatHistoryServiceImpl chatHistoryServiceImpl;

    public static ChatHistoryServiceImpl getChatHistoryService() {
        return chatHistoryServiceImpl;

    }
}
