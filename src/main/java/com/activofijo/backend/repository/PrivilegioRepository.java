package com.activofijo.backend.repository;

import com.activofijo.backend.models.Privilegio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegioRepository extends JpaRepository<Privilegio, Long> {

    /**
     * Buscar un privilegio por su nombre.
     */
    Optional<Privilegio> findByNombre(String nombre);
}
