package com.activofijo.backend.controller;

import com.activofijo.backend.dto.CuentaContableCreateDTO;
import com.activofijo.backend.dto.CuentaContableDTO;
import com.activofijo.backend.security.JwtUtil;
import com.activofijo.backend.services.CuentaContableService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cuentas-contables")
public class CuentaContableController {

    private static final Logger logger = LoggerFactory.getLogger(CuentaContableController.class);

    private final CuentaContableService cuentaService;
    private final JwtUtil jwtUtil;

    public CuentaContableController(CuentaContableService cuentaService, JwtUtil jwtUtil) {
        this.cuentaService = cuentaService;
        this.jwtUtil = jwtUtil;
    }

    private Long getAuthenticatedEmpresaId() {
        String token = (String) SecurityContextHolder.getContext()
                .getAuthentication().getCredentials();
        return jwtUtil.getEmpresaIdFromToken(token);
    }

    public static class CuentaRequest {
        @NotBlank public String codigo;
        @NotBlank public String nombre;
        @NotBlank public String tipo;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CuentaRequest body) {
        Long empresaId = getAuthenticatedEmpresaId();
        logger.info("➕ Crear cuenta '{}' para empresaId={}", body.codigo, empresaId);

        CuentaContableCreateDTO dto = new CuentaContableCreateDTO();
        dto.setCodigo(body.codigo);
        dto.setNombre(body.nombre);
        dto.setTipo(body.tipo);
        dto.setEmpresaId(empresaId);

        try {
            CuentaContableDTO creada = cuentaService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear cuenta contable: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> list() {
        Long empresaId = getAuthenticatedEmpresaId();
        logger.info("📋 Listar cuentas contables para empresaId={}", empresaId);
        return ResponseEntity.ok(cuentaService.listarPorEmpresa(empresaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CuentaRequest body) {
        Long empresaId = getAuthenticatedEmpresaId();
        logger.info("✏️ Actualizar cuenta id={} para empresaId={}", id, empresaId);

        CuentaContableCreateDTO dto = new CuentaContableCreateDTO();
        dto.setCodigo(body.codigo);
        dto.setNombre(body.nombre);
        dto.setTipo(body.tipo);
        dto.setEmpresaId(empresaId);

        try {
            CuentaContableDTO actualizada = cuentaService.actualizar(id, dto);
            return ResponseEntity.ok(actualizada);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al actualizar cuenta: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Long empresaId = getAuthenticatedEmpresaId();
        logger.info("🗑 Eliminar cuenta id={} para empresaId={}", id, empresaId);

        try {
            cuentaService.eliminar(id, empresaId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.warn("Error al eliminar cuenta: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
