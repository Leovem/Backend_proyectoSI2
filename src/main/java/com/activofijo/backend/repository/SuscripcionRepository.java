package com.activofijo.backend.repository;

import com.activofijo.backend.models.Suscripcion;
import com.activofijo.backend.models.Empresa;
import com.activofijo.backend.models.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
    List<Suscripcion> findByEmpresa(Empresa empresa);
    List<Suscripcion> findByPlan(Plan plan);
    List<Suscripcion> findByEstado(String estado);
}
