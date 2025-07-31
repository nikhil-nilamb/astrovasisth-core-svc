package com.vasisth.astrovasisth_core_svc.utilityService.impl;

import com.vasisth.astrovasisth_core_svc.utilityService.MobileMessageService;
import org.springframework.stereotype.Service;

@Service
public class MobileMessageServiceImpl implements MobileMessageService {

    @Override
    public boolean sendLoginOtpToMobile(String mobile, String otp) {
        return true; 
    }
}