package com.uas.pbo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mahasiswa_courses") // This will be the table name in the database
public class Mahasiswa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nim", nullable = false)
    private String nim; // The student's NIM (from APP_USER.IDENTIFIER)

    @Column(name = "course_code", nullable = false)
    private String courseCode; // The course code (from CLASS_LIST.COURSE_CODE)

    @Column(name = "status", nullable = false)
    private String status; // Will be set to "PENDING" by default

    // Constructors
    public Mahasiswa() {
    }

    public Mahasiswa(String nim, String courseCode, String status) {
        this.nim = nim;
        this.courseCode = courseCode;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
