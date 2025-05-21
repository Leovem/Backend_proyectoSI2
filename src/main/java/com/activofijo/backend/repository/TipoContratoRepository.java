package com.activofijo.backend.repository;

import com.activofijo.backend.models.TipoContrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoContratoRepository extends JpaRepository<TipoContrato, Long> {

    boolean existsByNombreIgnoreCase(String nombre);

    Optional<TipoContrato> findByNombreIgnoreCase(String nombre);
}
