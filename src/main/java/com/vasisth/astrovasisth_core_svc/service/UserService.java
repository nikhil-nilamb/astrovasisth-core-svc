package com.vasisth.astrovasisth_core_svc.service;

import com.vasisth.astrovasisth_core_svc.dto.AuthResponse;
import com.vasisth.astrovasisth_core_svc.dto.LoginRequest;

public interface UserService {
    AuthResponse getUserDetails(String token);
}
