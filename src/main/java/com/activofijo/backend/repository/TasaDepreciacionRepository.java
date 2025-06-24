package com.activofijo.backend.repository;


import com.activofijo.backend.models.TasaDepreciacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasaDepreciacionRepository extends JpaRepository<TasaDepreciacion, Long> {
}

