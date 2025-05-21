package com.activofijo.backend.repository;

import com.activofijo.backend.models.ClasificacionActivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClasificacionActivoRepository extends JpaRepository<ClasificacionActivo, Long> {

    boolean existsByNombreIgnoreCase(String nombre);

    Optional<ClasificacionActivo> findByNombreIgnoreCase(String nombre);
}
