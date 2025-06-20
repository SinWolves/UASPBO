package com.uas.pbo.config;

import com.uas.pbo.model.User;
import com.uas.pbo.model.ClassList;
import com.uas.pbo.repository.userRepository;
import com.uas.pbo.repository.ClassListRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
        userRepository userRepository,
        ClassListRepository classListRepository,
        PasswordEncoder passwordEncoder
    ) {
        return args -> {
            // Add Users if DB is empty
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

            // Add ClassList entries if DB is empty
            if (classListRepository.count() == 0) {
                classListRepository.save(new ClassList(
                    "CS101",
                    "Introduction to Programming",
                    3,
                    "2023/1",
                    "D-101",
                    "Dr. Jos Timanta Tarigan, S.Kom., M.Sc."
                ));

                classListRepository.save(new ClassList(
                    "CS102",
                    "Data Structures",
                    4,
                    "2023/2",
                    "D-102",
                    "Dr. Hendra Simanjuntak, M.Kom."
                ));

                classListRepository.save(new ClassList(
                    "CS103",
                    "Computer Networks",
                    3,
                    "2023/2",
                    "D-103",
                    "Dr. Suryanto, M.T."
                ));
            }
        };
    }
}
