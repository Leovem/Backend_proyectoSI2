package com.activofijo.backend.repository;

import com.activofijo.backend.models.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {

    boolean existsByNombreIgnoreCaseAndMarca_Id(String nombre, Long marcaId);

    Optional<Modelo> findByNombreIgnoreCaseAndMarca_Id(String nombre, Long marcaId);

    List<Modelo> findAllByMarca_Id(Long marcaId);
}
