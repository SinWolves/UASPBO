package com.uas.pbo.config;

import com.uas.pbo.model.User;
import com.uas.pbo.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {
    
    @Autowired
    private userRepository userRepository;  // Changed case to match your actual class

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/javascript/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/dosen/**").hasRole("DOSEN")
                .requestMatchers("/mahasiswa/**").hasRole("MAHASISWA")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(roleBasedRedirectHandler())
                .failureUrl("/login?error=true")
                .permitAll()
            );
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler roleBasedRedirectHandler() {
        return (request, response, authentication) -> {
            User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            
            switch (user.getRole().toUpperCase()) {
                case "ADMIN":
                    response.sendRedirect("/admin/home");
                    break;
                case "DOSEN":
                    response.sendRedirect("/dosen/home");
                    break;
                case "MAHASISWA":
                    response.sendRedirect("/mahasiswa/home");
                    break;
                default:
                    throw new IllegalStateException("Invalid role");
            }
        };
    }
}