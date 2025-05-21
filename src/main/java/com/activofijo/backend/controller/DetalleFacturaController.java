package com.activofijo.backend.controller;

import com.activofijo.backend.dto.DetalleFacturaCreateDTO;
import com.activofijo.backend.dto.DetalleFacturaDTO;
import com.activofijo.backend.models.Factura;
import com.activofijo.backend.repository.FacturaRepository;
import com.activofijo.backend.security.JwtUtil;
import com.activofijo.backend.services.DetalleFacturaService;
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
@RequestMapping("/api/detalles-factura")
public class DetalleFacturaController {

    private static final Logger logger = LoggerFactory.getLogger(DetalleFacturaController.class);

    private final DetalleFacturaService detalleService;
    private final FacturaRepository facturaRepository;
    private final JwtUtil jwtUtil;

    public DetalleFacturaController(DetalleFacturaService detalleService,
                                    FacturaRepository facturaRepository,
                                    JwtUtil jwtUtil) {
        this.detalleService = detalleService;
        this.facturaRepository = facturaRepository;
        this.jwtUtil = jwtUtil;
    }

    private Long getAuthenticatedEmpresaId() {
        String token = (String) SecurityContextHolder.getContext()
                .getAuthentication().getCredentials();
        return jwtUtil.getEmpresaIdFromToken(token);
    }

    private boolean validarEmpresaDeFactura(Long facturaId, Long empresaId) {
        Optional<Factura> factura = facturaRepository.findById(facturaId);
        return factura.isPresent() && factura.get().getEmpresa().getId().equals(empresaId);
    }

    public static class DetalleRequest {
        @NotNull public Long facturaId;
        @NotBlank public String descripcion;
        @NotNull @Min(1) public Integer cantidad;
        @NotNull @DecimalMin("0.00") public BigDecimal precioUnitario;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DetalleRequest body) {
        Long empresaId = getAuthenticatedEmpresaId();
        logger.info("➕ Crear detalle para facturaId={} (empresaId={})", body.facturaId, empresaId);

        if (!validarEmpresaDeFactura(body.facturaId, empresaId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene permiso sobre esta factura.");
        }

        DetalleFacturaCreateDTO dto = new DetalleFacturaCreateDTO();
        dto.setFacturaId(body.facturaId);
        dto.setDescripcion(body.descripcion);
        dto.setCantidad(body.cantidad);
        dto.setPrecioUnitario(body.precioUnitario);

        try {
            DetalleFacturaDTO creado = detalleService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear detalle: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/factura/{facturaId}")
    public ResponseEntity<?> listByFactura(@PathVariable Long facturaId) {
        Long empresaId = getAuthenticatedEmpresaId();
        logger.info("📋 Listar detalles para facturaId={} (empresaId={})", facturaId, empresaId);

        if (!validarEmpresaDeFactura(facturaId, empresaId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene permiso sobre esta factura.");
        }

        return ResponseEntity.ok(detalleService.listarPorFactura(facturaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody DetalleRequest body) {
        Long empresaId = getAuthenticatedEmpresaId();
        logger.info("✏️ Actualizar detalle id={} para facturaId={} (empresaId={})", id, body.facturaId, empresaId);

        if (!validarEmpresaDeFactura(body.facturaId, empresaId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene permiso sobre esta factura.");
        }

        DetalleFacturaCreateDTO dto = new DetalleFacturaCreateDTO();
        dto.setFacturaId(body.facturaId);
        dto.setDescripcion(body.descripcion);
        dto.setCantidad(body.cantidad);
        dto.setPrecioUnitario(body.precioUnitario);

        try {
            DetalleFacturaDTO actualizado = detalleService.actualizar(id, dto);
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
            detalleService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.warn("Error al eliminar detalle: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
