package com.uas.pbo.service;

import com.uas.pbo.exception.DuplicateApplicationException;
import com.uas.pbo.model.ClassList;
import com.uas.pbo.repository.ClassListRepository;
import com.uas.pbo.repository.DosenRepository;
import com.uas.pbo.repository.MahasiswaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassListService {

    private final ClassListRepository classListRepository;
    private final MahasiswaRepository mahasiswaRepository;
    private final DosenRepository dosenRepository;

    @Autowired
    public ClassListService(ClassListRepository classListRepository, MahasiswaRepository mahasiswaRepository, DosenRepository dosenRepository) {
        this.classListRepository = classListRepository;
        this.mahasiswaRepository = mahasiswaRepository;
        this.dosenRepository = dosenRepository;
    }

    /**
     * Retrieves all classes from the database.
     * @return A list of all ClassList objects.
     */
    public List<ClassList> getAllClasses() {
        return classListRepository.findAll();
    }

    /**
     * Saves a new class to the database.
     * @param classList The ClassList object to save.
     */
    public void addClass(ClassList classList) {
        if (classListRepository.existsByCourseCode(classList.getCourseCode())) {
            // If it exists, throw our custom exception with a clear message.
            throw new DuplicateApplicationException("This course code has already existed.");
        }
        // You could add validation logic here, e.g., check if courseCode already exists.
        classListRepository.save(classList);
    }

    /**
     * Deletes a class by its course code (primary key).
     * @param courseCode The ID of the class to delete.
     */
    
    public void deleteClass(String courseCode) {
        // FIRST, check if any student enrollments exist for this class.
        if (mahasiswaRepository.existsByCourseCode(courseCode)) {
            // If they exist, throw an exception with a clear message.
            throw new IllegalStateException("Cannot delete class. Students are currently enrolled in it. Please remove them first.");
        }
        // Pengecekan 2 (BARU): Apakah ada dosen yang ditugaskan?
        if (dosenRepository.existsByCourseCode(courseCode)) {
            throw new IllegalStateException("Cannot delete class. A lecturer is already assigned to it.");
        }
        
        // If no enrollments exist, then it's safe to delete the class.
        classListRepository.deleteById(courseCode);
    }
}