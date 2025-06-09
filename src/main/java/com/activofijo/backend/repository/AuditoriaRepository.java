package com.activofijo.backend.repository;

import com.activofijo.backend.models.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {

    List<Auditoria> findByEmpresaIdOrderByFechaClienteDesc(Long empresaId);

    List<Auditoria> findByUsuarioIdOrderByFechaClienteDesc(Long usuarioId);
}
