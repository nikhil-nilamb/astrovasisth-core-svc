package com.vasisth.astrovasisth_core_svc.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ColleagueResponse {
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
    private int approveCount;
}
