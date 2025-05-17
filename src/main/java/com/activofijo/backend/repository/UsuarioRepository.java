package com.activofijo.backend.repository;

import com.activofijo.backend.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su nombre de usuario dentro de una empresa.
     */
    Optional<Usuario> findByUsuarioAndEmpresaId(String usuario, Long empresaId);

    /**
     * Busca un usuario por su email dentro de una empresa.
     */
    Optional<Usuario> findByEmailAndEmpresaId(String email, Long empresaId);

    /**
     * Lista todos los usuarios de una empresa.
     */
    List<Usuario> findByEmpresaId(Long empresaId);

    /**
     * Lista todos los usuarios activos de una empresa.
     */
    List<Usuario> findByEmpresaIdAndActivoTrue(Long empresaId);

    /**
     * Lista todos los usuarios de un rol dentro de una empresa.
     */
    List<Usuario> findByRolIdAndEmpresaId(Long rolId, Long empresaId);
    // Lista todos los usuarios en la base de datos
    Optional<Usuario> findByUsuario(String usuario);

}
