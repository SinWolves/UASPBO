package com.uas.pbo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    private String identifier; // Stores NIM or NIP
    private String email;
    private String name;
    private String password;
    private String role; // "mahasiswa", "dosen", "admin"

    // Getter
    public String getIdentifier() {
        return identifier;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    //Setter
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}