package com.activofijo.backend.controller;

import com.activofijo.backend.dto.UsuarioCreateDTO;
import com.activofijo.backend.dto.UsuarioDTO;
import com.activofijo.backend.security.JwtUtil;
import com.activofijo.backend.services.UsuarioService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public UsuarioController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    private Long getAuthenticatedUserEmpresaId() {
        logger.info("⚡️ getAuthenticatedEmpresaId() invocado");
        String token = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials();
        logger.info("🔑 Token JWT obtenido: {}", token);
        Long empresaId = jwtUtil.getEmpresaIdFromToken(token);
        logger.info("🏢 Empresa ID extraído del token: {}", empresaId);
        return empresaId;

    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listAll() {
        logger.info("⚡️ listAll() invocado");
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("📋 listAll usuarios para empresaId={}", empresaId);
        List<UsuarioDTO> list = usuarioService.listAll(empresaId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/activos")
    public ResponseEntity<List<UsuarioDTO>> listActive() {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("📋 listActive usuarios para empresaId={}", empresaId);
        List<UsuarioDTO> list = usuarioService.listActive(empresaId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getById(@PathVariable Long id) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("🔍 getById usuario id={} en empresaId={}", id, empresaId);
        UsuarioDTO dto = usuarioService.findById(id);
        if (!dto.getEmpresaId().equals(empresaId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/search")
    public ResponseEntity<UsuarioDTO> findByUsuario(@RequestParam String usuario) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("🔍 findByUsuario='{}' en empresaId={}", usuario, empresaId);
        UsuarioDTO dto = usuarioService.findByUsuario(usuario, empresaId);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioCreateDTO body) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("🔄 update usuario id={} en empresaId={}", id, empresaId);
        // ensure the DTO refers to same empresa
        body.setEmpresaId(empresaId);
        UsuarioDTO updated = usuarioService.update(id, body);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("🛑 deactivate usuario id={} en empresaId={}", id, empresaId);
        // verify belongs to empresa
        UsuarioDTO dto = usuarioService.findById(id);
        if (!dto.getEmpresaId().equals(empresaId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        usuarioService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        Long empresaId = getAuthenticatedUserEmpresaId();
        logger.info("✅ activate usuario id={} en empresaId={}", id, empresaId);
        UsuarioDTO dto = usuarioService.findById(id);
        if (!dto.getEmpresaId().equals(empresaId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        usuarioService.activate(id);
        return ResponseEntity.noContent().build();
    }
}
