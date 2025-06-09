package com.activofijo.backend.repository;

import com.activofijo.backend.models.PagoSuscripcion;
import com.activofijo.backend.models.Suscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoSuscripcionRepository extends JpaRepository<PagoSuscripcion, Long> {
    List<PagoSuscripcion> findBySuscripcion(Suscripcion suscripcion);
    List<PagoSuscripcion> findByEstadoPago(String estadoPago);
}
