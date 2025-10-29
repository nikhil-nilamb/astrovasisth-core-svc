package com.vasisth.astrovasisth_core_svc.controller;

import com.vasisth.astrovasisth_core_svc.dto.ColleagueResponse;
import com.vasisth.astrovasisth_core_svc.dto.CustomerResponse;
import com.vasisth.astrovasisth_core_svc.dto.OtpVerificationRequest;
import com.vasisth.astrovasisth_core_svc.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customerAuth")
public class customarAuthController {

    private final CustomerService customerService;

    @PostMapping("/{emailOrMobile}")
    public ResponseEntity<CustomerResponse> validateCustomer(@PathVariable String emailOrMobile) {
        return ResponseEntity.ok(customerService.validateLogin(emailOrMobile));
    }

    @PostMapping("/validateOtp")
    public ResponseEntity<CustomerResponse> validateOtp(@RequestBody OtpVerificationRequest otpVerificationRequest) {
        return ResponseEntity.ok(customerService.validateOtp(otpVerificationRequest.getMobile(), otpVerificationRequest.getOtp()));
    }
}
