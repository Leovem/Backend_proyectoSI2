package com.activofijo.backend.repository;

import com.activofijo.backend.models.DetalleFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Long> {

    List<DetalleFactura> findAllByFactura_Id(Long facturaId);
}
