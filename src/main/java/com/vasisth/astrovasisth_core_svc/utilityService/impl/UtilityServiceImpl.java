package com.vasisth.astrovasisth_core_svc.utilityService.impl;

import ch.qos.logback.core.util.StringUtil;
import com.vasisth.astrovasisth_core_svc.utilityService.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UtilityServiceImpl implements UtilityService {

    private final MobileMessageService mobileMessageService;
    private final EmailService emailService;

    @Override
    public String manageOtp(String mobileNo, String email) {
        String otp = generateOtp();
        String otpMessage = String.format(UtilityConstant.OTP_MESSAGE, otp);
        System.out.println(otp);
        boolean smsSent = mobileMessageService.sendLoginOtpToMobile(mobileNo, otpMessage);
        boolean emailSent = emailService.sendLoginOtpToEmail(email,UtilityConstant.OTP_EMAIL_SUBJECT,  otpMessage);
        return otp;
    }

    private String generateOtp() {
        Random random = new Random();
        // Generate number between 100000 and 999999
        int otp = 100000 + random.nextInt(900000);
//        return String.valueOf(otp);
        return "111111"; // For testing purpose, returning a static OTP
    }
}
