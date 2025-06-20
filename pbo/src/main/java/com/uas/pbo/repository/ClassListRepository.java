package com.uas.pbo.repository;

import com.uas.pbo.model.ClassList;
import java.util.Optional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassListRepository extends JpaRepository<ClassList, String> {
    boolean existsByCourseCode(String courseCode);
    Optional<ClassList> findByCourseCode(String courseCode);
}
