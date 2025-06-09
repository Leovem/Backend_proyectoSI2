package com.activofijo.backend.repository;

import com.activofijo.backend.models.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    Optional<Plan> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
