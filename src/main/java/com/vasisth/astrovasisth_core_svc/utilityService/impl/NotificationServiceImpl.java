package com.vasisth.astrovasisth_core_svc.utilityService.impl;

import com.vasisth.astrovasisth_core_svc.utilityService.MobileMessageService;
import com.vasisth.astrovasisth_core_svc.utilityService.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public boolean sendNotificationToUser(String id, String message) {
        return false;
    }
}
