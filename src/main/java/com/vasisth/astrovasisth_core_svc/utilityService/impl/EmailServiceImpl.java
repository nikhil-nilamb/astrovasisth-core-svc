package com.vasisth.astrovasisth_core_svc.utilityService.impl;

import com.vasisth.astrovasisth_core_svc.utilityService.EmailService;
import com.vasisth.astrovasisth_core_svc.utilityService.MobileMessageService;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public boolean sendLoginOtpToEmail(String to, String subject, String message) {
        return false;
    }
}
