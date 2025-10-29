package com.vasisth.astrovasisth_core_svc.service;

import com.vasisth.astrovasisth_core_svc.constants.Channel;
import com.vasisth.astrovasisth_core_svc.constants.PersonStatus;
import com.vasisth.astrovasisth_core_svc.entity.Colleague;
import com.vasisth.astrovasisth_core_svc.entity.Customer;
import com.vasisth.astrovasisth_core_svc.entity.User;
import com.vasisth.astrovasisth_core_svc.repo.ColleagueRepository;
import com.vasisth.astrovasisth_core_svc.repo.CustomerRepository;
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

//    private final UserRepository userRepository;
    private final ColleagueRepository colleagueRepository;
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        String channel = id.split("&-&")[0];
        final String commingId = id.split("&-&")[1];
        switch (Channel.valueOf(channel)) {
            case MOBILE_CUSTOMER -> {
                Customer customer = customerRepository.findById(UUID.fromString(commingId))
                        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + commingId));

                return new org.springframework.security.core.userdetails.User(
                        customer.getFirstName() == null ? "My" : customer.getFirstName() + " " + customer.getLastName() == null ? "Name" : customer.getLastName(),
                        "sd",
                        true,
                        true,
                        true,
                        true,
                        Collections.singletonList(new SimpleGrantedAuthority("USER"))
                );
            }
            default -> {
                Colleague colleague = colleagueRepository.findById(UUID.fromString(id))
                        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + commingId));

                return new org.springframework.security.core.userdetails.User(
                        colleague.getFirstName() + " " + colleague.getLastName(),
                        colleague.getPassword(),
                        true,
                        true,
                        true,
                        true,
                        Collections.singletonList(new SimpleGrantedAuthority("USER"))
                );
            }
        }
    }
}