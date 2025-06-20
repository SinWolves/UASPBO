package com.uas.pbo.repository;

import com.uas.pbo.model.Mahasiswa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MahasiswaRepository extends JpaRepository<Mahasiswa, Long> {

    // Custom method to check if an enrollment already exists to prevent duplicates
    boolean existsByNimAndCourseCode(String nim, String courseCode);

        // ADD THIS NEW METHOD:
    // Spring Data JPA will automatically create the query for us based on the method name.
    List<Mahasiswa> findByNimAndStatus(String nim, String status);

        // ADD THIS NEW METHOD:
    boolean existsByCourseCode(String courseCode);
}