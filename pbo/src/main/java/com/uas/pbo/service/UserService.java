package com.uas.pbo.service;

import com.uas.pbo.model.User;
import com.uas.pbo.repository.userRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final userRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(userRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String identifier, String email, String name, String password, String role) {
        if (userRepository.existsByIdentifier(identifier)) {
            throw new RuntimeException("Identifier already exists");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = new User();
        user.setIdentifier(identifier);
        user.setEmail(email);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role.toUpperCase());
        
        userRepository.save(user);
    }
}