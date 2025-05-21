package com.activofijo.backend.controller;

import com.activofijo.backend.dto.ProveedorCreateDTO;
import com.activofijo.backend.dto.ProveedorDTO;
import com.activofijo.backend.security.JwtUtil;
import com.activofijo.backend.services.ProveedorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private static final Logger logger = LoggerFactory.getLogger(ProveedorController.class);

    private final ProveedorService proveedorService;
    private final JwtUtil jwtUtil;

    public ProveedorController(ProveedorService proveedorService, JwtUtil jwtUtil) {
        this.proveedorService = proveedorService;
        this.jwtUtil = jwtUtil;
    }

    private Long getAuthenticatedUserEmpresaId() {
        String token = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials();
        return jwtUtil.getEmpresaIdFromToken(token);
    }

    public static class ProveedorRequest {
        @NotBlank(message = "El nombre no puede estar vacío")
        public String nombre;

        public String contacto;
        public String email;
        public String telefono;
        public String direccion;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProveedorRequest body) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("➕ Crear proveedor '{}' para empresaId={}", body.nombre, empresaId);

        ProveedorCreateDTO dto = new ProveedorCreateDTO();
        dto.setNombre(body.nombre);
        dto.setContacto(body.contacto);
        dto.setEmail(body.email);
        dto.setTelefono(body.telefono);
        dto.setDireccion(body.direccion);
        dto.setEmpresaId(empresaId);

        try {
            ProveedorDTO proveedorCreado = proveedorService.crearProveedor(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(proveedorCreado);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear proveedor: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> list() {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("📋 Listar proveedores para empresaId={}", empresaId);

        return ResponseEntity.ok(proveedorService.listarProveedoresPorEmpresa(empresaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ProveedorRequest body) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("✏️ Actualizar proveedor id={} para empresaId={}", id, empresaId);

        ProveedorCreateDTO dto = new ProveedorCreateDTO();
        dto.setNombre(body.nombre);
        dto.setContacto(body.contacto);
        dto.setEmail(body.email);
        dto.setTelefono(body.telefono);
        dto.setDireccion(body.direccion);
        dto.setEmpresaId(empresaId);

        try {
            ProveedorDTO proveedorActualizado = proveedorService.actualizarProveedor(id, dto);
            return ResponseEntity.ok(proveedorActualizado);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al actualizar proveedor: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("🗑 Eliminar proveedor id={} para empresaId={}", id, empresaId);

        try {
            proveedorService.eliminarProveedor(id, empresaId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.warn("Error al eliminar proveedor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
