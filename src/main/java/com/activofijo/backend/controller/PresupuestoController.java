package com.activofijo.backend.controller;

import com.activofijo.backend.dto.PresupuestoCreateDTO;
import com.activofijo.backend.dto.PresupuestoDTO;
import com.activofijo.backend.security.JwtUtil;
import com.activofijo.backend.services.PresupuestoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/presupuestos")
public class PresupuestoController {

    private static final Logger logger = LoggerFactory.getLogger(PresupuestoController.class);
    private final PresupuestoService presupuestoService;
    private final JwtUtil jwtUtil;

    public PresupuestoController(PresupuestoService presupuestoService, JwtUtil jwtUtil) {
        this.presupuestoService = presupuestoService;
        this.jwtUtil = jwtUtil;
    }

    private Long getAuthenticatedUserEmpresaId() {
        String token = (String) SecurityContextHolder.getContext()
            .getAuthentication()
            .getCredentials();
        return jwtUtil.getEmpresaIdFromToken(token);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PresupuestoCreateDTO body) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        body.setEmpresaId(empresaId);

        logger.info("➕ Crear presupuesto '{}' para empresaId={}", body.getNombre(), empresaId);

        try {
            PresupuestoDTO creado = presupuestoService.crearPresupuesto(body);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException e) {
            logger.warn("❌ Error al crear presupuesto: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> list() {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("📋 Listar presupuestos para empresaId={}", empresaId);

        return ResponseEntity.ok(presupuestoService.listarPorEmpresa(empresaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody PresupuestoCreateDTO body) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        body.setEmpresaId(empresaId);

        logger.info("✏️ Actualizar presupuesto id={} para empresaId={}", id, empresaId);

        try {
            PresupuestoDTO actualizado = presupuestoService.actualizarPresupuesto(id, body);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            logger.warn("❌ Error al actualizar presupuesto: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("🗑 Eliminar presupuesto id={} para empresaId={}", id, empresaId);

        try {
            presupuestoService.eliminarPresupuesto(id, empresaId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.warn("❌ Error al eliminar presupuesto: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
public ResponseEntity<?> getById(@PathVariable Long id) {
    Long empresaId = getAuthenticatedUserEmpresaId();
    try {
        PresupuestoDTO dto = presupuestoService.obtenerPorId(id, empresaId);
        return ResponseEntity.ok(dto);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}

}
