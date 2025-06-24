package com.activofijo.backend.repository;



import com.activofijo.backend.models.DepreciacionActivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepreciacionActivoRepository extends JpaRepository<DepreciacionActivo, Long> {
}

