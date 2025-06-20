package com.uas.pbo.repository;

import com.uas.pbo.model.Dosen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DosenRepository extends JpaRepository<Dosen, String> {
    List<Dosen> findByNipAndStatus(String nip, String status);
}
