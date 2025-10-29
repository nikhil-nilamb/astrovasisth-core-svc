package com.vasisth.astrovasisth_core_svc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vasisth.astrovasisth_core_svc.dto.ChatHistoryReqRes;
import com.vasisth.astrovasisth_core_svc.dto.CustomerResponse;
import com.vasisth.astrovasisth_core_svc.service.ChatHistoryService;
import com.vasisth.astrovasisth_core_svc.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/chat")
public class ChathistoryController {

    private final ChatHistoryService chatHistoryService;

    @GetMapping("/history/{customerId}")
    public ResponseEntity<List<ChatHistoryReqRes>> getChatHistory(@PathVariable String customerId) {
        return ResponseEntity.ok(chatHistoryService.getChaHistoryByUserId(customerId));
    }

    @GetMapping("/history/{customerId}/{colleagueId}")
    public ResponseEntity<ChatHistoryReqRes> getChatDetails(@PathVariable String customerId,@PathVariable String colleagueId) throws JsonProcessingException {
        return ResponseEntity.ok(chatHistoryService.getChatsById(customerId, colleagueId));
    }

}
