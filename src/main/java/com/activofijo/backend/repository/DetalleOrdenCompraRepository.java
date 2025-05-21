package com.activofijo.backend.repository;

import com.activofijo.backend.models.DetalleOrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleOrdenCompraRepository extends JpaRepository<DetalleOrdenCompra, Long> {
    List<DetalleOrdenCompra> findAllByOrdenCompra_Id(Long ordenId);
}
