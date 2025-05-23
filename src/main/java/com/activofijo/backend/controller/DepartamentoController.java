package com.activofijo.backend.controller;

import com.activofijo.backend.dto.DepartamentoCreateDTO;
import com.activofijo.backend.dto.DepartamentoDTO;
import com.activofijo.backend.dto.DepartamentoUpdateDTO;
import com.activofijo.backend.security.JwtUtil;
import com.activofijo.backend.services.DepartamentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoController {

    private static final Logger logger = LoggerFactory.getLogger(DepartamentoController.class);

    private final DepartamentoService departamentoService;
    private final JwtUtil jwtUtil;

    public DepartamentoController(DepartamentoService departamentoService, JwtUtil jwtUtil) {
        this.departamentoService = departamentoService;
        this.jwtUtil = jwtUtil;
    }

    private Long getAuthenticatedUserEmpresaId() {
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return jwtUtil.getEmpresaIdFromToken(token);
    }

    public static class DepartamentoRequest {
        @NotBlank(message = "El nombre no puede estar vacío")
        public String nombre;
        public Long responsableId;
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody DepartamentoRequest body) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("➕ Crear departamento '{}' para empresaId={}", body.nombre, empresaId);

        DepartamentoCreateDTO dto = new DepartamentoCreateDTO();
        dto.setNombre(body.nombre);
        dto.setResponsableId(body.responsableId);
        dto.setEmpresaId(empresaId);

        try {
            DepartamentoDTO creado = departamentoService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear departamento: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("📋 Listar departamentos para empresaId={}", empresaId);
        return ResponseEntity.ok(departamentoService.listarPorEmpresa(empresaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody DepartamentoRequest body) {
        logger.info("✏️ Actualizar departamento id={} con nombre={}", id, body.nombre);

        DepartamentoUpdateDTO dto = new DepartamentoUpdateDTO();
        dto.setNombre(body.nombre);
        dto.setResponsableId(body.responsableId);

        try {
            DepartamentoDTO actualizado = departamentoService.actualizar(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al actualizar departamento: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        logger.info("🗑 Eliminar departamento id={}", id);
        try {
            departamentoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.warn("Error al eliminar departamento: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
