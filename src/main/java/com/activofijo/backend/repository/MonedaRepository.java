package com.activofijo.backend.repository;

import com.activofijo.backend.models.Moneda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonedaRepository extends JpaRepository<Moneda, String> {
    // El ID de Moneda es el código (ej: "BOB", "USD")
}
