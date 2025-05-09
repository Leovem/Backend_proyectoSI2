package com.activofijo.backend.controller;

import com.activofijo.backend.DTO.PrivilegioDTO;
import com.activofijo.backend.services.PrivilegioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/privilegios")
public class PrivilegiosController {

    @Autowired
    private PrivilegioService privilegioService;

    // Crear privilegio
    @PostMapping
    public ResponseEntity<PrivilegioDTO> crearPrivilegio(@RequestBody PrivilegioDTO privilegioDTO) {
        return ResponseEntity.ok(privilegioService.crearPrivilegio(privilegioDTO));
    }

    // Obtener todos los privilegios
    @GetMapping
    public ResponseEntity<List<PrivilegioDTO>> obtenerTodos() {
        return ResponseEntity.ok(privilegioService.obtenerTodos());
    }

    // Obtener privilegio por ID
    @GetMapping("/{id}")
    public ResponseEntity<PrivilegioDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(privilegioService.obtenerPorId(id));
    }

    // Eliminar privilegio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrivilegio(@PathVariable Long id) {
        privilegioService.eliminarPrivilegio(id);
        return ResponseEntity.noContent().build();
    }
}
