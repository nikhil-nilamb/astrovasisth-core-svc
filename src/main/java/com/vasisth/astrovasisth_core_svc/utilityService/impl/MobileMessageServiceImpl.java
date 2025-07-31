package com.vasisth.astrovasisth_core_svc.utilityService.impl;

import com.vasisth.astrovasisth_core_svc.utilityService.MobileMessageService;
import org.springframework.stereotype.Service;
//import com.twilio.Twilio;
//import com.twilio.rest.verify.v2.service.Verification;

@Service
public class MobileMessageServiceImpl implements MobileMessageService {

//    public static final String ACCOUNT_SID = "AC4c829194100c2277135bd6fd1084c76e";
//    public static final String AUTH_TOKEN = "[AuthToken]";

    @Override
    public boolean sendLoginOtpToMobile(String mobile, String otp) {
        return true;
    }


//        public static void sendMessage(String message, String mobileNo) {
//            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//            Verification verification = Verification.creator(
//                            "VA354467c264ade0d207ce83320ecc0e4c",
//                            "+919852141476",
//                            "sms")
//                    .create();
//
//            System.out.println(verification.getSid());
//    }
}
