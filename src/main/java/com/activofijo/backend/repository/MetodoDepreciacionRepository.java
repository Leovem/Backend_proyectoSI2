package com.activofijo.backend.repository;

import com.activofijo.backend.models.MetodoDepreciacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetodoDepreciacionRepository extends JpaRepository<MetodoDepreciacion, Long> {

    boolean existsByNombreIgnoreCase(String nombre);

    Optional<MetodoDepreciacion> findByNombreIgnoreCase(String nombre);
}
