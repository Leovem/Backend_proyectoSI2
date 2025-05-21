package com.activofijo.backend.repository;

import com.activofijo.backend.models.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    boolean existsByNumeroAndEmpresa_Id(String numero, Long empresaId);

    List<Factura> findAllByEmpresa_Id(Long empresaId);
}
