package com.activofijo.backend.repository;


import com.activofijo.backend.models.BajaActivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BajaActivoRepository extends JpaRepository<BajaActivo, Long> {
}
