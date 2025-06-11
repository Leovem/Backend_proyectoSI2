package com.activofijo.backend.controller;

import com.activofijo.backend.dto.RolDTO;
import com.activofijo.backend.security.JwtUtil;
import com.activofijo.backend.services.RoleService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    private final RoleService roleService;
    private final JwtUtil jwtUtil;

    public RoleController(RoleService roleService, JwtUtil jwtUtil) {
        this.roleService = roleService;
        this.jwtUtil = jwtUtil;
    }

    private Long getAuthenticatedUserEmpresaId() {
        String token = (String) SecurityContextHolder.getContext()
            .getAuthentication()
            .getCredentials();
        return jwtUtil.getEmpresaIdFromToken(token);
    }

    private String getAuthenticatedUsername() {
        String token = (String) SecurityContextHolder.getContext()
                .getAuthentication().getCredentials();
        return jwtUtil.getUsernameFromToken(token);
    }

    private String extraerIpCliente(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0]; // En caso de múltiples proxies
    }

    @GetMapping
    public ResponseEntity<List<RolDTO>> list() {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("📋 list roles para empresaId={}", empresaId);
        List<RolDTO> roles = roleService.listByEmpresa(empresaId);
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolDTO> getById(@PathVariable Long id) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("🔍 get role id={} en empresaId={}", id, empresaId);
        // filtrar entre los de la empresa
        return roleService.listByEmpresa(empresaId).stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public static class RoleRequest {
        @NotBlank(message = "El nombre no puede estar vacío")
        public String nombre;
        public List<Long> privilegioIds;
    }

    @PostMapping
    public ResponseEntity<RolDTO> create(
            @Valid @RequestBody RoleRequest body,
            HttpServletRequest httpRequest) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        String ipCliente = extraerIpCliente(httpRequest);
        String username = getAuthenticatedUsername();

        logger.info("➕ create role '{}' en empresaId={}", body.nombre, empresaId);
        RolDTO dto = new RolDTO();
        dto.setNombre(body.nombre);
        dto.setEmpresaId(empresaId);
        RolDTO created = roleService.create(dto, body.privilegioIds, ipCliente, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody RoleRequest body) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("✏️ update role id={} en empresaId={}", id, empresaId);
        RolDTO dto = new RolDTO();
        dto.setId(id);
        dto.setNombre(body.nombre);
        dto.setEmpresaId(empresaId);
        RolDTO updated = roleService.update(id, dto, body.privilegioIds);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("🗑 delete role id={} en empresaId={}", id, empresaId);
        // asegurarse que existe y pertenece
        boolean exists = roleService.listByEmpresa(empresaId).stream()
                .anyMatch(r -> r.getId().equals(id));
        if (!exists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/privilegios")
public ResponseEntity<RolDTO> asignarPrivilegios(
        @PathVariable Long id,
        @RequestBody List<Long> privilegioIds) {
    
    Long empresaId = getAuthenticatedUserEmpresaId();
    logger.info("🔐 asignar privilegios a rol id={} (empresaId={})", id, empresaId);
    
    // Validar que el rol pertenece a la empresa
    boolean exists = roleService.listByEmpresa(empresaId).stream()
            .anyMatch(r -> r.getId().equals(id));
    if (!exists) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    RolDTO updated = roleService.actualizarPrivilegios(id, privilegioIds);
    return ResponseEntity.ok(updated);
}

}
