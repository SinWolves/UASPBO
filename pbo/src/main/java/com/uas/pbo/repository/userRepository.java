package com.uas.pbo.repository;

import com.uas.pbo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface userRepository extends JpaRepository<User, String> {
    @Query("SELECT u FROM User u WHERE u.identifier = :usernameOrEmail OR u.email = :usernameOrEmail")
    Optional<User> findByIdentifierOrEmail(@Param("usernameOrEmail") String usernameOrEmail);
    
    boolean existsByIdentifier(String identifier);
    boolean existsByEmail(String email);
}