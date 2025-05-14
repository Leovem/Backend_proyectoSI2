package com.activofijo.backend.repository;

import com.activofijo.backend.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    /**
     * Buscar todos los roles de una empresa determinada.
     */
    List<Rol> findByEmpresaId(Long empresaId);

    /**
     * Buscar un rol por nombre dentro de una empresa.
     */
    Optional<Rol> findByNombreAndEmpresaId(String nombre, Long empresaId);
}
