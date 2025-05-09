package com.activofijo.backend.controller;

import com.activofijo.backend.DTO.LoginRequest;
import com.activofijo.backend.DTO.UsuarioDTO;
import com.activofijo.backend.DTO.JwtResponse;
import com.activofijo.backend.models.Usuario;
import com.activofijo.backend.security.JwtUtil;
import com.activofijo.backend.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    System.out.println("📥 Intentando login con usuario: " + request.getUsuario());

    Optional<Usuario> usuarioOpt = usuarioService.buscarPorUsuario(request.getUsuario());

    if (usuarioOpt.isPresent()) {
        Usuario usuario = usuarioOpt.get();
        System.out.println("👤 Usuario encontrado en BD: " + usuario.getUsuario());
        boolean match = passwordEncoder.matches(request.getContrasena(), usuario.getContrasena());
        System.out.println("🔐 Contraseña coincide: " + match);

        if (match) {
            String token = jwtUtil.generateToken(usuario.getNombreCompleto(), usuario.getRol().getNombre());
            System.out.println("✅ Token generado: " + token);
            return ResponseEntity.ok(
                new JwtResponse(token, usuario.getNombreCompleto(), usuario.getRol().getNombre())
            );
        } else {
            System.out.println("❌ Contraseña incorrecta");
        }
    } else {
        System.out.println("❌ Usuario no encontrado");
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
}



    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioDTO dto) {
        try {
            Usuario nuevo = usuarioService.crearUsuario(dto);
            return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
