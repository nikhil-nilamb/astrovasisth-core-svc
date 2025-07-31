package com.vasisth.astrovasisth_core_svc.service.impl;

import com.vasisth.astrovasisth_core_svc.dto.*;
import com.vasisth.astrovasisth_core_svc.entity.User;
import com.vasisth.astrovasisth_core_svc.exception.CustomException;
import com.vasisth.astrovasisth_core_svc.repo.UserRepository;
import com.vasisth.astrovasisth_core_svc.service.AuthService;
import com.vasisth.astrovasisth_core_svc.service.CustomUserDetailsService;
import com.vasisth.astrovasisth_core_svc.service.JwtService;
import com.vasisth.astrovasisth_core_svc.utilityService.UtilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UtilityService utilityService;

    @Override
    public boolean signup(SignupRequest request) {

        // Check if email and mobile no  is already taken
        if (userRepository.findByEmailOrMobile(request.getEmail(),request.getMobile()).isPresent()) {
            throw new CustomException(userRepository.findByEmail(request.getEmail()).isPresent()
                    ? "User with this email is already registered, please login through email"
                    : "User with this mobile is already registered, please login through mobile");
        }

        User user = new User();
        user.setFullName(request.getEmail());
        user.setEmail(request.getEmail());
        user.setMobile(request.getMobile());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActive(true);
        userRepository.save(user);

        // Generate JWT token
//        String token = jwtService.generateToken(user.getEmail());
        String otp = utilityService.manageOtp(request.getMobile(),request.getEmail());
        user.setOtp(passwordEncoder.encode(otp));
        userRepository.save(user);
        return true;
    }

    @Override
    public AuthResponse verifyOtp(OtpVerificationRequest request) {
        User user = userRepository.findByMobile(request.getMobile())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getOtp(), user.getOtp())) {
            throw new CustomException("Please check your OTP and try again");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token, user.getEmail());
    }

    @Override
    public boolean loginVaiMobile(LoginRequest request) {
        User user = userRepository.findByEmailOrMobile(request.getUsername(), request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with mobile: " + request.getUsername()));
        String otp =  utilityService.manageOtp(user.getMobile(), user.getEmail());
        user.setOtp(passwordEncoder.encode(otp));
        userRepository.save(user);
        return true;
    }

    @Override
    public AuthResponse loginVaiUsernameAndPassword(LoginRequest request) {
        User user = userRepository.findByEmailOrMobile(request.getUsername(), request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with mobile: " + request.getUsername()));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException("Please check your Password and try again");
        }
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token, user.getEmail());
    }

    @Override
    public void processForgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));

        // Add your password reset logic here
        // For example: generate reset token, send email, etc.
    }

}
