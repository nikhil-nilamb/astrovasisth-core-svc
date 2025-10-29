package com.vasisth.astrovasisth_core_svc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatHistoryReqRes {
    private UUID id;
    private String message;
    private String customerId;
    private String customerName;
    private String colleagueId;
    private String colleagueName;
    private String lastTimeStamp;
    private int unreadMessages;
    private String colleagueAvatar;

}
