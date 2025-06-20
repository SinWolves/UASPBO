package com.uas.pbo.service;

import com.uas.pbo.exception.DuplicateApplicationException;
import com.uas.pbo.model.Mahasiswa;
import com.uas.pbo.repository.MahasiswaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MahasiswaService {

    private final MahasiswaRepository mahasiswaRepository;

    @Autowired
    public MahasiswaService(MahasiswaRepository mahasiswaRepository) {
        this.mahasiswaRepository = mahasiswaRepository;
    }

    /**
     * Handles the business logic for a student applying to a class.
     * @param nim The student's NIM.
     * @param courseCode The course code they are applying for.
     * @throws DuplicateApplicationException if the student has already applied for this class.
     */
    public void applyForClass(String nim, String courseCode) {
        // 1. Business Rule: Check if an application already exists.
        if (mahasiswaRepository.existsByNimAndCourseCode(nim, courseCode)) {
            // If it exists, throw our custom exception with a clear message.
            throw new DuplicateApplicationException("You have already applied for this class.");
        }

        // 2. If no duplicate is found, create the new enrollment record.
        Mahasiswa newEnrollment = new Mahasiswa(nim, courseCode, "PENDING");

        // 3. Save the new record to the database.
        mahasiswaRepository.save(newEnrollment);
    }

    
    // ADD THIS NEW METHOD:
    /**
     * Finds all approved class enrollments for a given student.
     * @param nim The student's NIM.
     * @return A list of Mahasiswa objects with "APPROVED" status.
     */
    public List<Mahasiswa> getApprovedClassesForStudent(String nim) {
        return mahasiswaRepository.findByNimAndStatus(nim, "APPROVED");
    }

}