package com.activofijo.backend.controller;

import com.activofijo.backend.dto.FacturaCreateDTO;
import com.activofijo.backend.dto.FacturaDTO;
import com.activofijo.backend.security.JwtUtil;
import com.activofijo.backend.services.FacturaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private static final Logger logger = LoggerFactory.getLogger(FacturaController.class);

    private final FacturaService facturaService;
    private final JwtUtil jwtUtil;

    public FacturaController(FacturaService facturaService, JwtUtil jwtUtil) {
        this.facturaService = facturaService;
        this.jwtUtil = jwtUtil;
    }

    private Long getAuthenticatedEmpresaId() {
        String token = (String) SecurityContextHolder.getContext()
                .getAuthentication().getCredentials();
        return jwtUtil.getEmpresaIdFromToken(token);
    }

    // Inner DTO para entrada desde el controlador (opcional)
    public static class FacturaRequest {
        @NotBlank public String numero;
        @NotNull public LocalDate fecha;
        public Long proveedorId;
        public Long usuarioId;
        public Long ordenCompraId;
        public Long presupuestoId;
        public Long cuentaContableId;
        @NotNull @DecimalMin("0.0") public BigDecimal total;
        @NotBlank public String monedaCodigo;
        @NotBlank public String tipoPago; // "Contado" o "Credito"
        public String observaciones;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody FacturaRequest body) {
        Long empresaId = getAuthenticatedEmpresaId();
        logger.info("➕ Crear factura '{}' para empresaId={}", body.numero, empresaId);

        FacturaCreateDTO dto = new FacturaCreateDTO();
        dto.setNumero(body.numero);
        dto.setFecha(body.fecha);
        dto.setProveedorId(body.proveedorId);
        dto.setUsuarioId(body.usuarioId);
        dto.setOrdenCompraId(body.ordenCompraId);
        dto.setPresupuestoId(body.presupuestoId);
        dto.setCuentaContableId(body.cuentaContableId);
        dto.setEmpresaId(empresaId);
        dto.setTotal(body.total);
        dto.setMonedaCodigo(body.monedaCodigo);
        dto.setTipoPago(body.tipoPago);
        dto.setObservaciones(body.observaciones);

        try {
            FacturaDTO creada = facturaService.crearFactura(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear factura: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> list() {
        Long empresaId = getAuthenticatedEmpresaId();
        logger.info("📋 Listar facturas para empresaId={}", empresaId);
        return ResponseEntity.ok(facturaService.listarFacturasPorEmpresa(empresaId));
    }
}
