package com.vasisth.astrovasisth_core_svc.service;

import com.vasisth.astrovasisth_core_svc.constants.PersonStatus;
import com.vasisth.astrovasisth_core_svc.entity.User;
import com.vasisth.astrovasisth_core_svc.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));

        return new org.springframework.security.core.userdetails.User(
                user.getFullName(),
                user.getPassword(),
                user.getActive() == PersonStatus.ACTIVE,
                user.getActive() == PersonStatus.ACTIVE,
                user.getActive() == PersonStatus.ACTIVE,
                user.getActive() == PersonStatus.ACTIVE,
                Collections.singletonList(new SimpleGrantedAuthority("USER"))
        );
    }
}