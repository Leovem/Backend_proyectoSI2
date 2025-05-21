package com.activofijo.backend.repository;

import com.activofijo.backend.models.CuentaContable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaContableRepository extends JpaRepository<CuentaContable, Long> {

    boolean existsByCodigoAndEmpresa_Id(String codigo, Long empresaId);

    List<CuentaContable> findAllByEmpresa_Id(Long empresaId);
}
