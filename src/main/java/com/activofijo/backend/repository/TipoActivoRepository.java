package com.activofijo.backend.repository;

import com.activofijo.backend.models.TipoActivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoActivoRepository extends JpaRepository<TipoActivo, Long> {

    boolean existsByNombreIgnoreCase(String nombre);

    Optional<TipoActivo> findByNombreIgnoreCase(String nombre);
}
