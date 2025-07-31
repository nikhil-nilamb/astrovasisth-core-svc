package com.vasisth.astrovasisth_core_svc.utilityService;

public interface MobileMessageService {
    boolean sendMobileMessage(String message);
    boolean sendEmail(String to, String subject, String body);
    boolean sendNotification(String message);
}
