package com.activofijo.backend.controller;

import com.activofijo.backend.dto.DetalleOrdenCompraCreateDTO;
import com.activofijo.backend.dto.DetalleOrdenCompraDTO;
import com.activofijo.backend.models.OrdenCompra;
import com.activofijo.backend.repository.OrdenCompraRepository;
import com.activofijo.backend.security.JwtUtil;
import com.activofijo.backend.services.DetalleOrdenCompraService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/detalles-orden")
public class DetalleOrdenCompraController {

    private static final Logger logger = LoggerFactory.getLogger(DetalleOrdenCompraController.class);

    private final DetalleOrdenCompraService detalleService;
    private final OrdenCompraRepository ordenCompraRepository;
    private final JwtUtil jwtUtil;

    public DetalleOrdenCompraController(DetalleOrdenCompraService detalleService,
                                        OrdenCompraRepository ordenCompraRepository,
                                        JwtUtil jwtUtil) {
        this.detalleService = detalleService;
        this.ordenCompraRepository = ordenCompraRepository;
        this.jwtUtil = jwtUtil;
    }

    private Long getAuthenticatedEmpresaId() {
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return jwtUtil.getEmpresaIdFromToken(token);
    }

    public static class DetalleRequest {
        @NotNull public Long ordenId;
        @NotBlank public String descripcion;
        @NotNull @Min(1) public Integer cantidad;
        @NotNull @DecimalMin("0.00") public BigDecimal precioEstimado;
    }

    private boolean validarEmpresaDeOrden(Long ordenId, Long empresaId) {
        Optional<OrdenCompra> orden = ordenCompraRepository.findById(ordenId);
        return orden.isPresent() && orden.get().getEmpresa().getId().equals(empresaId);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DetalleRequest body) {
        Long empresaId = getAuthenticatedEmpresaId();
        logger.info("➕ Crear detalle para ordenId={} (empresaId={})", body.ordenId, empresaId);

        if (!validarEmpresaDeOrden(body.ordenId, empresaId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene permiso sobre esta orden.");
        }

        DetalleOrdenCompraCreateDTO dto = new DetalleOrdenCompraCreateDTO();
        dto.setOrdenId(body.ordenId);
        dto.setDescripcion(body.descripcion);
        dto.setCantidad(body.cantidad);
        dto.setPrecioEstimado(body.precioEstimado);

        try {
            DetalleOrdenCompraDTO creado = detalleService.crearDetalle(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear detalle: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/orden/{ordenId}")
    public ResponseEntity<?> listByOrden(@PathVariable Long ordenId) {
        Long empresaId = getAuthenticatedEmpresaId();
        logger.info("📋 Listar detalles para ordenId={} (empresaId={})", ordenId, empresaId);

        if (!validarEmpresaDeOrden(ordenId, empresaId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene permiso sobre esta orden.");
        }

        return ResponseEntity.ok(detalleService.listarPorOrden(ordenId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody DetalleRequest body) {
        Long empresaId = getAuthenticatedEmpresaId();
        logger.info("✏️ Actualizar detalle id={} para ordenId={} (empresaId={})", id, body.ordenId, empresaId);

        if (!validarEmpresaDeOrden(body.ordenId, empresaId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene permiso sobre esta orden.");
        }

        DetalleOrdenCompraCreateDTO dto = new DetalleOrdenCompraCreateDTO();
        dto.setOrdenId(body.ordenId);
        dto.setDescripcion(body.descripcion);
        dto.setCantidad(body.cantidad);
        dto.setPrecioEstimado(body.precioEstimado);

        try {
            DetalleOrdenCompraDTO actualizado = detalleService.actualizarDetalle(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al actualizar detalle: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        logger.info("🗑 Eliminar detalle id={}", id);

        try {
            detalleService.eliminarDetalle(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.warn("Error al eliminar detalle: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
