package com.uas.pbo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dosens")
public class Dosen {

    @Id
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
}
