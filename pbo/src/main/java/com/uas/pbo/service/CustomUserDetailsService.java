package com.uas.pbo.service;

import com.uas.pbo.model.User;
import com.uas.pbo.repository.userRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final userRepository userRepository;

    public CustomUserDetailsService(userRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        return userRepository.findByIdentifierOrEmail(usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with identifier or email: " + usernameOrEmail));
    }
}