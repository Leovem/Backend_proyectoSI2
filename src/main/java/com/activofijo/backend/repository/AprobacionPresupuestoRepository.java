package com.activofijo.backend.repository;


import com.activofijo.backend.models.AprobacionPresupuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AprobacionPresupuestoRepository extends JpaRepository<AprobacionPresupuesto, Long> {
}

