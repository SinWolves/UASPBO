package com.uas.pbo.config;

import com.uas.pbo.model.User;
import com.uas.pbo.repository.userRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initDatabase(userRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setIdentifier("ADM-0177");
                admin.setEmail("maulana31@gmail.com");
                admin.setName("Maulana Hasim");
                admin.setPassword(passwordEncoder.encode("@Dm0177"));
                admin.setRole("ADMIN");
                userRepository.save(admin);

                User dosen = new User();
                dosen.setIdentifier("1993112523041052");
                dosen.setEmail("auliatanjung93@gmail.com");
                dosen.setName("Aulia Akbar Tanjung");
                dosen.setPassword(passwordEncoder.encode("D$n052"));
                dosen.setRole("DOSEN");
                userRepository.save(dosen);

                User mahasiswa = new User();
                mahasiswa.setIdentifier("251402117");
                mahasiswa.setEmail("falkrynst222@gmail.com");
                mahasiswa.setName("Ahmad Falkry Nasution");
                mahasiswa.setPassword(passwordEncoder.encode("Mh$117"));
                mahasiswa.setRole("MAHASISWA");
                userRepository.save(mahasiswa);
            }
        };
    }
}