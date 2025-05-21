package com.activofijo.backend.repository;

import com.activofijo.backend.models.Presupuesto;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {
    boolean existsByNombreAndEmpresa_Id(String nombre, Long empresaId);
    List<Presupuesto> findAllByEmpresa_Id(Long empresaId);
}
