package com.activofijo.backend.controller;

import com.activofijo.backend.dto.JwtResponse;
import com.activofijo.backend.dto.LoginRequest;
import com.activofijo.backend.dto.UsuarioCreateDTO;
import com.activofijo.backend.dto.UsuarioDTO;
import com.activofijo.backend.exception.BadRequestException;
import com.activofijo.backend.services.AuthService;
import com.activofijo.backend.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService,
                          JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    private Long getAuthenticatedEmpresaId() {
        String token = (String) SecurityContextHolder.getContext()
                .getAuthentication().getCredentials();
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


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        
        String ipCliente = extraerIpCliente(httpRequest);
        UsuarioDTO usuarioDTO = authService.login(request, ipCliente);
        
        logger.info("[AuthController] Intentando login desde IP {}", ipCliente);
        logger.info("[AuthController] Usuario recibido: {}", request.getUsuario());
        String token = jwtUtil.generateToken(
            usuarioDTO.getUsuario(),
            usuarioDTO.getRolNombre(),
            usuarioDTO.getEmpresaId()
        );
        logger.debug("[AuthController] token generado={}", token);

        JwtResponse resp = new JwtResponse(
            token,
            usuarioDTO.getUsuario(),
            usuarioDTO.getNombreCompleto(),
            usuarioDTO.getRolNombre(),
            usuarioDTO.getEmpresaId()
        );

        logger.info("[AuthController] login() respuesta lista");
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UsuarioCreateDTO dto, HttpServletRequest request) {
        String ipCliente = extraerIpCliente(request);
        String username = getAuthenticatedUsername();
        Long empresaId = getAuthenticatedEmpresaId();  // 👈 extrae el empresaId del token
        dto.setEmpresaId(empresaId);                   // 👈 así ya no lo necesitas en el JSON

        logger.info("📝 Intentando registrar usuario: {} (empresaId={})",
                    dto.getUsuario(), empresaId);

        try {
            UsuarioDTO created = authService.register(dto, ipCliente, username);
            logger.info("✅ Usuario registrado: {} (id={})", created.getUsuario(), created.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(created);

        } catch (BadRequestException e) {
            logger.warn("❌ Datos inválidos al registrar: {} — {}", dto.getUsuario(), e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("❌ Error inesperado en registro: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error interno del servidor");
        }
    }
}
