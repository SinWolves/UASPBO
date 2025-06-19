package com.uas.pbo.repository;

import com.uas.pbo.model.Dosen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DosenRepository extends JpaRepository<Dosen, String> {
    
}
