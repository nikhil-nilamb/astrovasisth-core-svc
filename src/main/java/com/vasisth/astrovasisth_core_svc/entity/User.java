package com.vasisth.astrovasisth_core_svc.entity;

import com.vasisth.astrovasisth_core_svc.constants.PersonStatus;
import com.vasisth.astrovasisth_core_svc.constants.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    private String fullName;
    private String email;
    private String mobile;
    private String password;
    private String otp;
    private Role role;
    private PersonStatus active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
