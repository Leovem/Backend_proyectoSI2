package com.activofijo.backend.repository;

import com.activofijo.backend.models.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {

    boolean existsByNombreIgnoreCaseAndEmpresa_Id(String nombre, Long empresaId);

    Optional<Ubicacion> findByNombreIgnoreCaseAndEmpresa_Id(String nombre, Long empresaId);

    List<Ubicacion> findAllByEmpresa_Id(Long empresaId);
}
