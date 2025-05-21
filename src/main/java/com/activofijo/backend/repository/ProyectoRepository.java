package com.activofijo.backend.repository;

import com.activofijo.backend.models.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    boolean existsByNombreAndEmpresa_Id(String nombre, Long empresaId);
    List<Proyecto> findAllByEmpresa_Id(Long empresaId);

}
