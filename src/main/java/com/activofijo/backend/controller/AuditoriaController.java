package com.activofijo.backend.controller;

import com.activofijo.backend.dto.AuditoriaDTO;
import com.activofijo.backend.security.JwtUtil;
import com.activofijo.backend.services.AuditoriaServiceList;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditoria")
public class AuditoriaController {

    private final AuditoriaServiceList auditoriaService;
    private final JwtUtil jwtUtil;

    public AuditoriaController(AuditoriaServiceList auditoriaService, JwtUtil jwtUtil) {
        this.auditoriaService = auditoriaService;
        this.jwtUtil = jwtUtil;
    }

    private Long getEmpresaId() {
        String token = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials();
        return jwtUtil.getEmpresaIdFromToken(token);
    }


    @GetMapping("/empresa")
    public ResponseEntity<List<AuditoriaDTO>> listarPorEmpresa() {
        return ResponseEntity.ok(auditoriaService.listarPorEmpresa(getEmpresaId()));
    }

}
