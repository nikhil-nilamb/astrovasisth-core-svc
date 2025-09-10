package com.vasisth.astrovasisth_core_svc.controller;

import com.vasisth.astrovasisth_core_svc.dto.AuthResponse;
import com.vasisth.astrovasisth_core_svc.dto.GlobalResponse;
import com.vasisth.astrovasisth_core_svc.service.JwtService;
import com.vasisth.astrovasisth_core_svc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/user/details")
    public ResponseEntity<GlobalResponse<AuthResponse>> getUserDetails(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(new GlobalResponse<>(0,"Successfully",userService.getUserDetails(jwtService.getToken(token))));
    }
}
