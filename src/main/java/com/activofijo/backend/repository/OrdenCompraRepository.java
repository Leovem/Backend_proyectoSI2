package com.activofijo.backend.repository;

import com.activofijo.backend.models.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {

    // Puedes agregar métodos personalizados si lo necesitas, por ejemplo:
    boolean existsByNumeroAndEmpresaId(String numero, Long empresaId);
}
