package com.uas.pbo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uas.pbo.exception.DuplicateApplicationException;
import com.uas.pbo.model.Dosen;
import com.uas.pbo.repository.DosenRepository;



@Service
public class DosenService {

    private final DosenRepository dosenRepository;

    @Autowired
    public DosenService(DosenRepository  dosenRepository) {
        this.dosenRepository = dosenRepository;
    }

    /**
     * Handles the business logic for a student applying to a class.
     * @param nip The student's NIp.
     * @param courseCode The course code they are applying for.
     * @throws DuplicateApplicationException if the student has already applied for this class.
     */
    public void applyForClass(String nip, String courseCode) {
        // 1. Business Rule: Check if an application already exists.
        if (dosenRepository.existsByNipAndCourseCode(nip, courseCode)) {
            // If it exists, throw our custom exception with a clear message.
            throw new DuplicateApplicationException("You have already applied for this class.");
        }

        // 2. If no duplicate is found, create the new enrollment record.
        Dosen newEnrollment = new Dosen(nip, courseCode, "PENDING");

        // 3. Save the new record to the database.
        dosenRepository.save(newEnrollment);
    }

    
    // ADD THIS NEW METHOD:
    /**
     * Finds all approved class enrollments for a given student.
     * @param nip The student's NIp.
     * @return A list of Mahasiswa objects with "APPROVED" status.
     */
    public List<Dosen> getApprovedClassesForStudent(String nip) {
        return dosenRepository.findByNipAndStatus(nip, "APPROVED");
    }

}
