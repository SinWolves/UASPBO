package com.uas.pbo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.uas.pbo.model.User;
import java.util.List;
import java.util.Optional;

public interface userRepository extends JpaRepository<User, String> {

    // Basic CRUD operations are inherited from JpaRepository
    
    // Custom query methods
    Optional<User> findByIdentifier(String identifier);
    
    Optional<User> findByEmail(String email);
    
    List<User> findByRole(String role);
    
    @Query("SELECT u FROM User u WHERE u.role = :role AND u.name LIKE %:name%")
    List<User> findByRoleAndNameContaining(@Param("role") String role, @Param("name") String name);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
    long countByRole(@Param("role") String role);
    
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.email = :email")
    void updatePassword(@Param("email") String email, @Param("newPassword") String newPassword);
}