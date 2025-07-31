package com.vasisth.astrovasisth_core_svc.service;

import com.vasisth.astrovasisth_core_svc.dto.*;

public interface AuthService {
    boolean signup(SignupRequest request);
    AuthResponse verifyOtp(OtpVerificationRequest request);
    boolean loginVaiMobile(LoginRequest request);
    AuthResponse loginVaiUsernameAndPassword(LoginRequest request);
    void processForgotPassword(ForgotPasswordRequest request);
}