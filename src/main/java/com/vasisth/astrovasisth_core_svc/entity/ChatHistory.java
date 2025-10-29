package com.vasisth.astrovasisth_core_svc.entity;

import com.vasisth.astrovasisth_core_svc.constants.ColleagueProfileStatus;
import com.vasisth.astrovasisth_core_svc.constants.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "chat_history")
@NoArgsConstructor
public class ChatHistory {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    private UUID customerId;
    @Lob
    @Column(columnDefinition="TEXT")
    private String message;
    private UUID colleagueId;
}
