package com.activofijo.backend.repository;

import com.activofijo.backend.models.TipoDepreciacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoDepreciacionRepository extends JpaRepository<TipoDepreciacion, Long> {

    boolean existsByNombreIgnoreCase(String nombre);

    Optional<TipoDepreciacion> findByNombreIgnoreCase(String nombre);
}
