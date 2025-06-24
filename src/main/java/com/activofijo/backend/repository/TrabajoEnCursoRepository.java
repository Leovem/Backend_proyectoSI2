package com.activofijo.backend.repository;


import com.activofijo.backend.models.TrabajoEnCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrabajoEnCursoRepository extends JpaRepository<TrabajoEnCurso, Long> {
}
