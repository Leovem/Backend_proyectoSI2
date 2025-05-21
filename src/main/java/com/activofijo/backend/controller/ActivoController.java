package com.activofijo.backend.controller;

import com.activofijo.backend.dto.ActivoCreateDTO;
import com.activofijo.backend.dto.ActivoDTO;
import com.activofijo.backend.security.JwtUtil;
import com.activofijo.backend.services.ActivoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/activos")
public class ActivoController {

    private static final Logger logger = LoggerFactory.getLogger(ActivoController.class);

    private final ActivoService activoService;
    private final JwtUtil jwtUtil;

    public ActivoController(ActivoService activoService, JwtUtil jwtUtil) {
        this.activoService = activoService;
        this.jwtUtil = jwtUtil;
    }

    private Long getAuthenticatedEmpresaId() {
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return jwtUtil.getEmpresaIdFromToken(token);
    }

    public static class ActivoRequest {
        @NotBlank public String codigo;
        @NotBlank public String nombre;
        public String descripcion;

        @NotNull public Long tipoId;
        public Long metodoDepreciacionId;
        public Long tipoDepreciacionId;
        public Long grupoId;
        public Long clasificacionId;
        public Long marcaId;
        public Long modeloId;
        public Long contratoId;
        public Long ubicacionId;
        public Long facturaId;
        public Long cuentaContableId;

        @NotNull public BigDecimal valorInicial;
        @NotBlank public String monedaCodigo;
        @NotNull public LocalDate fechaAdquisicion;
        @NotBlank public String estado;

        public Boolean activo = true;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ActivoRequest body) {
        Long empresaId = getAuthenticatedEmpresaId();
        logger.info("➕ Crear activo '{}' para empresaId={}", body.codigo, empresaId);

        ActivoCreateDTO dto = new ActivoCreateDTO();
        dto.setCodigo(body.codigo);
        dto.setNombre(body.nombre);
        dto.setDescripcion(body.descripcion);
        dto.setTipoId(body.tipoId);
        dto.setMetodoDepreciacionId(body.metodoDepreciacionId);
        dto.setTipoDepreciacionId(body.tipoDepreciacionId);
        dto.setGrupoId(body.grupoId);
        dto.setClasificacionId(body.clasificacionId);
        dto.setMarcaId(body.marcaId);
        dto.setModeloId(body.modeloId);
        dto.setContratoId(body.contratoId);
        dto.setUbicacionId(body.ubicacionId);
        dto.setFacturaId(body.facturaId);
        dto.setCuentaContableId(body.cuentaContableId);
        dto.setEmpresaId(empresaId);
        dto.setValorInicial(body.valorInicial);
        dto.setMonedaCodigo(body.monedaCodigo);
        dto.setFechaAdquisicion(body.fechaAdquisicion);
        dto.setEstado(body.estado);
        dto.setActivo(body.activo);

        try {
            ActivoDTO creado = activoService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear activo: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> list() {
        Long empresaId = getAuthenticatedEmpresaId();
        logger.info("📋 Listar activos para empresaId={}", empresaId);
        return ResponseEntity.ok(activoService.listarPorEmpresa(empresaId));
    }
}
