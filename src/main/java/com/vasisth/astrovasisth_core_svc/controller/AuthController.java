package com.vasisth.astrovasisth_core_svc.controller;

import com.vasisth.astrovasisth_core_svc.dto.*;
import com.vasisth.astrovasisth_core_svc.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<GlobalResponse<Boolean>> signup(@Valid @RequestBody SignupRequest request) {
        return ResponseEntity.ok(new GlobalResponse<>(0,"Sign up Successfully",authService.signup(request)));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<GlobalResponse<AuthResponse>> verifyOtp(@Valid @RequestBody OtpVerificationRequest request) {
        AuthResponse authResponse = authService.verifyOtp(request);
        return ResponseEntity.ok(new GlobalResponse<>(
                0,
                !authResponse.getToken().equals("") ? "OTP verified successfully" : "Invalid OTP",
                authResponse
        ));
    }


    @PostMapping("/login")
    public ResponseEntity<GlobalResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        if(request.getLoginType().equals("MOBILE")){
            if(!authService.loginVaiMobile(request)) {
                return ResponseEntity.badRequest().body(new GlobalResponse<>(1, "Login failed", null));
            }
        } else {
            AuthResponse authResponse = authService.loginVaiUsernameAndPassword(request);
            if(authResponse == null) {
                return ResponseEntity.badRequest().body(new GlobalResponse<>(1, "Login failed", null));
            }
            return ResponseEntity.ok(new GlobalResponse<>(0, "Login successful", authResponse));
        }
        return null;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        authService.processForgotPassword(request);
        return ResponseEntity.ok().build();
    }

}
