package com.vasisth.astrovasisth_core_svc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CustomerResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String dob;
    private String gender;
    private String email;
    private String mobile;
    private String address;
    private String profileStatus;
    boolean isNewUser;
    private String token;
}
