package com.vasisth.astrovasisth_core_svc.entity;

import com.vasisth.astrovasisth_core_svc.constants.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {
    private String message;
    private String customerId;
    private String colleagueId;
    private String sentBy;
    private String timestamp;
    private boolean read;
}
