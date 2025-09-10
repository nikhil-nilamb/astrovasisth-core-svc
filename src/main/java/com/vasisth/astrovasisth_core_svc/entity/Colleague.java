package com.vasisth.astrovasisth_core_svc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "colleague")
@NoArgsConstructor
public class Colleague {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String designation;
    private String linkedInProfile;
    private String skills;
    private String experience;
    private String location;
    private String profileImageUrl;
    private boolean isActive = false;
    private boolean isDeleted = false;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID userId;
    private UUID updatedBy;
    private UUID createdBy;
    private String verifiedBy;
    private int approveCount = 0;
}
