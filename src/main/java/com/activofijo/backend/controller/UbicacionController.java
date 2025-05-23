package com.activofijo.backend.controller;

import com.activofijo.backend.dto.UbicacionCreateDTO;
import com.activofijo.backend.dto.UbicacionDTO;
import com.activofijo.backend.security.JwtUtil;
import com.activofijo.backend.services.UbicacionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

//import java.util.List;

@RestController
@RequestMapping("/api/ubicaciones")
public class UbicacionController {

    private static final Logger logger = LoggerFactory.getLogger(UbicacionController.class);

    private final UbicacionService ubicacionService;
    private final JwtUtil jwtUtil;

    public UbicacionController(UbicacionService ubicacionService, JwtUtil jwtUtil) {
        this.ubicacionService = ubicacionService;
        this.jwtUtil = jwtUtil;
    }

    private Long getAuthenticatedUserEmpresaId() {
        String token = (String) SecurityContextHolder.getContext()
                .getAuthentication().getCredentials();
        return jwtUtil.getEmpresaIdFromToken(token);
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody UbicacionCreateDTO dto) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        dto.setEmpresaId(empresaId);
        logger.info("➕ Crear ubicación '{}' para empresaId={}", dto.getNombre(), empresaId);

        try {
            UbicacionDTO creada = ubicacionService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear ubicación: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarPorEmpresa() {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("📋 Listar ubicaciones para empresaId={}", empresaId);

        return ResponseEntity.ok(ubicacionService.listarPorEmpresa(empresaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody UbicacionCreateDTO dto) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        dto.setEmpresaId(empresaId);
        logger.info("✏️ Actualizar ubicación id={} para empresaId={}", id, empresaId);

        try {
            UbicacionDTO actualizada = ubicacionService.actualizar(id, dto);
            return ResponseEntity.ok(actualizada);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al actualizar ubicación: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("🗑 Eliminar ubicación id={} para empresaId={}", id, empresaId);

        try {
            ubicacionService.eliminar(id, empresaId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.warn("Error al eliminar ubicación: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
