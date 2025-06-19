package com.uas.pbo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

    @Column(name = "mata_kuliah")
    private String mataKuliah;

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

    public String getMataKuliah() {
        return mataKuliah;
    }

    public void setMataKuliah(String mataKuliah) {
        this.mataKuliah = mataKuliah;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
