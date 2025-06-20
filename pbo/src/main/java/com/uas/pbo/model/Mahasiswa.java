package com.uas.pbo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "mahasiswa_courses")
public class Mahasiswa {

    // FIX 1: Use an auto-generated Long ID as the unique primary key for each enrollment.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // This is now a regular column, not the primary key.
    @Column(name = "nim", nullable = false)
    private String nim;

    // This is now a regular column.
    @Column(name = "course_code", nullable = false)
    private String courseCode;

    @Column(name = "status", nullable = false)
    private String status;

    // FIX 2: The relationship is Many-To-One, not One-To-One.
    // This allows you to get the full User object from an enrollment if needed.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nim", referencedColumnName = "IDENTIFIER", insertable = false, updatable = false)
    private User user;

    // FIX 2: The relationship is Many-To-One, not One-To-One.
    // This allows you to get the full ClassList object from an enrollment if needed.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_code", referencedColumnName = "COURSE_CODE", insertable = false, updatable = false)
    private ClassList classList;


    // Constructors
    public Mahasiswa() {
    }

    public Mahasiswa(String nim, String courseCode, String status) {
        this.nim = nim;
        this.courseCode = courseCode;
        this.status = status;
    }

    // Getters and Setters (I've added the missing ID getter/setter)

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ClassList getClassList() {
        return classList;
    }

    public void setClassList(ClassList classList) {
        this.classList = classList;
    }
}