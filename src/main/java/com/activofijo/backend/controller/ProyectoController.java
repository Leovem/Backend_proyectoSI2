package com.activofijo.backend.controller;

import com.activofijo.backend.dto.ProyectoCreateDTO;
import com.activofijo.backend.dto.ProyectoDTO;
import com.activofijo.backend.security.JwtUtil;
import com.activofijo.backend.services.ProyectoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    private static final Logger logger = LoggerFactory.getLogger(ProyectoController.class);

    private final ProyectoService proyectoService;
    private final JwtUtil jwtUtil;

    public ProyectoController(ProyectoService proyectoService, JwtUtil jwtUtil) {
        this.proyectoService = proyectoService;
        this.jwtUtil = jwtUtil;
    }

    private Long getAuthenticatedUserEmpresaId() {
        String token = (String) SecurityContextHolder.getContext()
            .getAuthentication()
            .getCredentials();
        return jwtUtil.getEmpresaIdFromToken(token);
    }

    public static class ProyectoRequest {
        @NotBlank(message = "El nombre no puede estar vacío")
        public String nombre;
        public String descripcion;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProyectoRequest body) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("➕ Crear proyecto '{}' para empresaId={}", body.nombre, empresaId);

        ProyectoCreateDTO dto = new ProyectoCreateDTO();
        dto.setNombre(body.nombre);
        dto.setDescripcion(body.descripcion);
        dto.setEmpresaId(empresaId);

        try {
            ProyectoDTO proyectoCreado = proyectoService.crearProyecto(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(proyectoCreado);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear proyecto: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> list() {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("📋 Listar proyectos para empresaId={}", empresaId);

        return ResponseEntity.ok(proyectoService.listarProyectosPorEmpresa(empresaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ProyectoRequest body) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("✏️ Actualizar proyecto id={} para empresaId={}", id, empresaId);

        ProyectoCreateDTO dto = new ProyectoCreateDTO();
        dto.setNombre(body.nombre);
        dto.setDescripcion(body.descripcion);
        dto.setEmpresaId(empresaId);

        try {
            ProyectoDTO proyectoActualizado = proyectoService.actualizarProyecto(id, dto);
            return ResponseEntity.ok(proyectoActualizado);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al actualizar proyecto: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("🗑 Eliminar proyecto id={} para empresaId={}", id, empresaId);

        try {
            proyectoService.eliminarProyecto(id, empresaId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.warn("Error al eliminar proyecto: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
