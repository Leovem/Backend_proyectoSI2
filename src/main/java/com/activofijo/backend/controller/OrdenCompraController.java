package com.activofijo.backend.controller;

import com.activofijo.backend.dto.OrdenCompraCreateDTO;
import com.activofijo.backend.dto.OrdenCompraDTO;
import com.activofijo.backend.security.JwtUtil;
import com.activofijo.backend.services.OrdenCompraService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/ordenes-compra")
public class OrdenCompraController {

    private static final Logger logger = LoggerFactory.getLogger(OrdenCompraController.class);

    private final OrdenCompraService ordenCompraService;
    private final JwtUtil jwtUtil;

    public OrdenCompraController(OrdenCompraService ordenCompraService, JwtUtil jwtUtil) {
        this.ordenCompraService = ordenCompraService;
        this.jwtUtil = jwtUtil;
    }

    private Long getAuthenticatedUserEmpresaId() {
        String token = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials();
        return jwtUtil.getEmpresaIdFromToken(token);
    }

    public static class OrdenCompraRequest {
        @NotBlank
        public String numero;

        @NotNull
        public LocalDate fecha;

        public Long proveedorId;
        public Long usuarioId;
        public Long presupuestoId;

        @NotBlank
        public String estado;

        public String observaciones;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody OrdenCompraRequest body) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("➕ Crear orden '{}' para empresaId={}", body.numero, empresaId);

        OrdenCompraCreateDTO dto = new OrdenCompraCreateDTO();
        dto.setNumero(body.numero);
        dto.setFecha(body.fecha);
        dto.setProveedorId(body.proveedorId);
        dto.setUsuarioId(body.usuarioId);
        dto.setPresupuestoId(body.presupuestoId);
        dto.setEmpresaId(empresaId);
        dto.setEstado(body.estado);
        dto.setObservaciones(body.observaciones);

        try {
            OrdenCompraDTO creada = ordenCompraService.crearOrden(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear orden: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> list() {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("📋 Listar órdenes de compra para empresaId={}", empresaId);

        return ResponseEntity.ok(ordenCompraService.listarPorEmpresa(empresaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody OrdenCompraRequest body) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("✏️ Actualizar orden id={} para empresaId={}", id, empresaId);

        OrdenCompraCreateDTO dto = new OrdenCompraCreateDTO();
        dto.setNumero(body.numero);
        dto.setFecha(body.fecha);
        dto.setProveedorId(body.proveedorId);
        dto.setUsuarioId(body.usuarioId);
        dto.setPresupuestoId(body.presupuestoId);
        dto.setEmpresaId(empresaId);
        dto.setEstado(body.estado);
        dto.setObservaciones(body.observaciones);

        try {
            OrdenCompraDTO actualizada = ordenCompraService.actualizarOrden(id, dto);
            return ResponseEntity.ok(actualizada);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al actualizar orden: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("🗑 Eliminar orden id={} para empresaId={}", id, empresaId);

        try {
            ordenCompraService.eliminarOrden(id, empresaId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.warn("Error al eliminar orden: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
