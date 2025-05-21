package com.activofijo.backend.repository;

import com.activofijo.backend.models.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

    boolean existsByNombreIgnoreCase(String nombre);

    Optional<Marca> findByNombreIgnoreCase(String nombre);
}
