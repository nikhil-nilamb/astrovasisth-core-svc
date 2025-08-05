package com.vasisth.astrovasisth_core_svc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String id;
    private String fullName;
    private String email;
    private String mobile;
    private String role;
    private String status;
    private String token;
}