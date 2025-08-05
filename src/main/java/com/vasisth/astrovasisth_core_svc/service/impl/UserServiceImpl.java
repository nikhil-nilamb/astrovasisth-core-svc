package com.vasisth.astrovasisth_core_svc.service.impl;

import com.vasisth.astrovasisth_core_svc.dto.AuthResponse;
import com.vasisth.astrovasisth_core_svc.entity.User;
import com.vasisth.astrovasisth_core_svc.exception.CustomException;
import com.vasisth.astrovasisth_core_svc.exception.GlobalExceptionHandler;
import com.vasisth.astrovasisth_core_svc.repo.UserRepository;
import com.vasisth.astrovasisth_core_svc.service.JwtService;
import com.vasisth.astrovasisth_core_svc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService tokenService;

    @Override
    public AuthResponse getUserDetails(String token) {
        String claimToken  = tokenService.extractId(token);
        UUID userId = null;
        if(claimToken !=null){
            userId = UUID.fromString(claimToken);
        } else {
            throw new CustomException("Invalid token");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found"));
        return  createAuthResponse(token , user);
    }

    private AuthResponse createAuthResponse(String token , User user) {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setEmail(user.getEmail());
        authResponse.setMobile(user.getMobile());
        authResponse.setFullName(user.getFullName());
        authResponse.setRole(user.getRole().toString());
        authResponse.setId(user.getId().toString());
        return authResponse;
    }
}
