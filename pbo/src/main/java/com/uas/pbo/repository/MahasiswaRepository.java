package com.uas.pbo.repository;

import com.uas.pbo.model.Mahasiswa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MahasiswaRepository extends JpaRepository<Mahasiswa, Long> {

    // Custom method to check if an enrollment already exists to prevent duplicates
    boolean existsByNimAndCourseCode(String nim, String courseCode);
}