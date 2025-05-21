package com.activofijo.backend.repository;

import com.activofijo.backend.models.Proveedor;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    boolean existsByNombreAndEmpresaId(String nombre, Long empresaId);
    List<Proveedor> findAllByEmpresa_Id(Long empresaId);
    List<Proveedor> findByEmpresaId(Long empresaId);
}
