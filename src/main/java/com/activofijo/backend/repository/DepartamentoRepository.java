package com.activofijo.backend.repository;

import com.activofijo.backend.models.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    // List<Departamento> findAllByEmpresa_Id(Long empresaId);
    List<Departamento> findAllByEmpresa_Id(Long empresaId);
    List<Departamento> findByEmpresaId(Long empresaId);
    

}
