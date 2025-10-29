package com.vasisth.astrovasisth_core_svc.service;

import com.vasisth.astrovasisth_core_svc.dto.CustomerResponse;

public interface CustomerService {
    CustomerResponse validateLogin(String emailOrMobile);
    CustomerResponse validateOtp(String emailOrMobile, String otp);
    CustomerResponse getCustomer(String id);
    CustomerResponse saveCustomer(CustomerResponse customerResponse);
}
