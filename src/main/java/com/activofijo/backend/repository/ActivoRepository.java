package com.activofijo.backend.repository;

import com.activofijo.backend.models.Activo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivoRepository extends JpaRepository<Activo, Long> {

    boolean existsByCodigoAndEmpresa_Id(String codigo, Long empresaId);

    List<Activo> findAllByEmpresa_Id(Long empresaId);
}
