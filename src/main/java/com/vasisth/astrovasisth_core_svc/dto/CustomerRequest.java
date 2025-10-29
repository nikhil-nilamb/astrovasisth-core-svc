package com.vasisth.astrovasisth_core_svc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    private UUID id;
    private String firstName;
    private String lastName;
    private String password;
    private String dob;
    private String gender;
    private String email;
    private String mobile;
    private String address;
}
