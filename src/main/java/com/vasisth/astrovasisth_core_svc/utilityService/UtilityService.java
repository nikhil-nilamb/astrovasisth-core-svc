package com.vasisth.astrovasisth_core_svc.utilityService;

public interface EmailService {
    boolean sendLoginOtpToEmail(String to,String subject, String message);
}