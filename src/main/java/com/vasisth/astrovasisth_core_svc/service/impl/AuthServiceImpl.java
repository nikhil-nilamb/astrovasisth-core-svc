package com.vasisth.astrovasisth_core_svc.service.impl;

import com.vasisth.astrovasisth_core_svc.constants.PersonStatus;
import com.vasisth.astrovasisth_core_svc.constants.Role;
import com.vasisth.astrovasisth_core_svc.dto.*;
import com.vasisth.astrovasisth_core_svc.entity.Colleague;
import com.vasisth.astrovasisth_core_svc.entity.User;
import com.vasisth.astrovasisth_core_svc.exception.CustomException;
import com.vasisth.astrovasisth_core_svc.repo.ColleagueRepository;
import com.vasisth.astrovasisth_core_svc.repo.UserRepository;
import com.vasisth.astrovasisth_core_svc.service.AuthService;
import com.vasisth.astrovasisth_core_svc.service.ColleagueService;
import com.vasisth.astrovasisth_core_svc.service.CustomUserDetailsService;
import com.vasisth.astrovasisth_core_svc.service.JwtService;
import com.vasisth.astrovasisth_core_svc.utilityService.UtilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UtilityService utilityService;
    private final ColleagueRepository colleagueRepository;

    @Override
    public boolean signup(SignupRequest request) {

        // Check if email and mobile no  is already taken
        if (userRepository.findByEmailOrMobile(request.getEmail(),request.getMobile()).isPresent()) {
            throw new CustomException(userRepository.findByEmail(request.getEmail()).isPresent()
                    ? "User with this email is already registered, please login through email"
                    : "User with this mobile is already registered, please login through mobile");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setMobile(request.getMobile());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRole()));
        user.setActive(PersonStatus.INACTIVE);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
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
        Colleague colleague = colleagueRepository.findBymobile(request.getMobile())
                .orElseThrow(() -> new UsernameNotFoundException("Colleague not found with email: " + request.getMobile()));
//        User user = userRepository.findByMobile(request.getMobile())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getOtp(), colleague.getOtp())) {
            throw new CustomException("Please check your OTP and try again");
        }

        colleague.setUpdatedAt(LocalDateTime.now());
        colleague.setActive(true);
//        colleague.setActive(PersonStatus.ACTIVE);
        colleagueRepository.save(colleague);

        String token = jwtService.generateToken(colleague.getId().toString());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setEmail(colleague.getEmail());
        authResponse.setMobile(colleague.getMobile());
        authResponse.setFullName(colleague.getFirstName() + " " + colleague.getLastName());
        authResponse.setRole(colleague.getRole().toString());
        authResponse.setId(colleague.getId().toString());
        authResponse.setProfileStatus(colleague.getProfileStatus().name());
        return authResponse;
    }

    @Override
    public boolean loginVaiMobile(LoginRequest request) {
        Colleague colleague = colleagueRepository.findBymobile(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Colleague not found with mobile: " + request.getUsername()));
        String otp =  utilityService.manageOtp(colleague.getMobile(), colleague.getEmail());
        colleague.setOtp(passwordEncoder.encode(otp));
        colleagueRepository.save(colleague);
        return true;
    }

    @Override
    public AuthResponse loginVaiUsernameAndPassword(LoginRequest request) {
        Colleague colleague = colleagueRepository.findByEmailOrMobile(request.getUsername(), request.getUsername())
                .orElseThrow(() -> new CustomException("Colleague not found with mobile: " + request.getUsername()));
//        User user = userRepository.findByEmailOrMobile(request.getUsername(), request.getUsername())
//                .orElseThrow(() -> new CustomException("User not found with mobile: " + request.getUsername()));
        if (!passwordEncoder.matches(request.getPassword(), colleague.getPassword())) {
            throw new CustomException("Please check your Password and try again");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(colleague.getId(), request.getPassword())
        );
        String token = jwtService.generateToken(colleague.getId().toString());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setEmail(colleague.getEmail());
        authResponse.setMobile(colleague.getMobile());
        authResponse.setFullName(colleague.getFirstName() + " " + colleague.getLastName());
        authResponse.setRole(colleague.getRole().toString());
        authResponse.setId(colleague.getId().toString());
        authResponse.setProfileStatus(colleague.getProfileStatus().name());
        return authResponse;
    }

    @Override
    public void processForgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));

        // Add your password reset logic here
        // For example: generate reset token, send email, etc.
    }

}
