package com.vasisth.astrovasisth_core_svc.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpVerificationRequest {
    @NotBlank(message = "Mobile number is required")
    private String mobile;
    @NotBlank(message = "OTP is required")
    private String otp;
}