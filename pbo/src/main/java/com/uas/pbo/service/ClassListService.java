package com.uas.pbo.service;

import com.uas.pbo.model.ClassList;
import com.uas.pbo.repository.ClassListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassListService {

    private final ClassListRepository classListRepository;

    @Autowired
    public ClassListService(ClassListRepository classListRepository) {
        this.classListRepository = classListRepository;
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
        // You could add validation logic here, e.g., check if courseCode already exists.
        classListRepository.save(classList);
    }

    /**
     * Deletes a class by its course code (primary key).
     * @param courseCode The ID of the class to delete.
     */
    public void deleteClass(String courseCode) {
        classListRepository.deleteById(courseCode);
    }
}