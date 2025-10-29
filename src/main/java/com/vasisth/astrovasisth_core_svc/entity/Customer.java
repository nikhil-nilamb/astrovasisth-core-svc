package com.vasisth.astrovasisth_core_svc.entity;

import com.vasisth.astrovasisth_core_svc.constants.ColleagueProfileStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "customer")
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    private String firstName;
    private String lastName;
    private String dob;
    private String gender;
    private String email;
    private String mobile;
    private String password;
    private String address;
    private boolean isActive = false;
    private boolean isDeleted = false;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ColleagueProfileStatus profileStatus = ColleagueProfileStatus.INCOMPLETE;
    private String otp;
}
