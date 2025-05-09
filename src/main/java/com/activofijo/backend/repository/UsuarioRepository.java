package com.activofijo.backend.repository;

import com.activofijo.backend.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsuario(String usuario);
    Optional<Usuario> findByEmail(String email);
    boolean existsByUsuario(String usuario);
    boolean existsByEmail(String email);
    List<Usuario> findByActivoTrue();
    Optional<Usuario> findById(Long id);
    List<Usuario> findAllByOrderByIdAsc();
}
