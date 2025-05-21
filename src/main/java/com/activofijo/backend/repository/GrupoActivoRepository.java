package com.activofijo.backend.repository;

import com.activofijo.backend.models.GrupoActivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GrupoActivoRepository extends JpaRepository<GrupoActivo, Long> {

    boolean existsByNombreIgnoreCase(String nombre);

    Optional<GrupoActivo> findByNombreIgnoreCase(String nombre);
}
