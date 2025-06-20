package com.uas.pbo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dosens")
public class Dosen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nip")
    private String nip;

    @OneToOne
    @JoinColumn(name = "nip", referencedColumnName = "IDENTIFIER", insertable = false, updatable = false)
    private User user;

    @Column(name = "course_code")
    private String courseCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_code", referencedColumnName = "COURSE_CODE", insertable = false, updatable = false)
    private ClassList classList;

    @Column(name = "status")
    private String status;

    public Dosen() {
    }

    public Dosen(String nip, String courseCode, String status) {
        this.nip = nip;
        this.courseCode = courseCode;
        this.status = status;
    }
    // Getters and Setters
    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
