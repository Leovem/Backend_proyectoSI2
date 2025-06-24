package com.activofijo.backend.repository;


import com.activofijo.backend.models.RevaluacionActivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevaluacionActivoRepository extends JpaRepository<RevaluacionActivo, Long> {
}

