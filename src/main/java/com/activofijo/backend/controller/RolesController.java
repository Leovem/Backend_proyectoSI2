package com.activofijo.backend.controller;

import com.activofijo.backend.DTO.PrivilegioDTO;
import com.activofijo.backend.DTO.RoleDTO;
import com.activofijo.backend.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    @Autowired
    private RoleService roleService;

    // Crear nuevo rol
    @PostMapping
    public ResponseEntity<RoleDTO> crearRole(@RequestBody RoleDTO roleDTO) {
        RoleDTO roleCreado = roleService.crearRole(roleDTO);
        return ResponseEntity.ok(roleCreado);
    }

    // Obtener todos los roles
    @GetMapping
    public ResponseEntity<List<RoleDTO>> obtenerTodos() {
        return ResponseEntity.ok(roleService.obtenerTodos());
    }

    // Obtener rol por ID
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.obtenerPorId(id));
    }

    // Actualizar rol
    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> actualizarRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleService.actualizarRole(id, roleDTO));
    }

    // Eliminar rol
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRole(@PathVariable Long id) {
        roleService.eliminarRole(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{rolId}/asignarprivilegio")
    public ResponseEntity<Void> asignarPrivilegios(
            @PathVariable Long rolId,
            @RequestBody List<Long> privilegioIds) {
        roleService.asignarPrivilegiosARol(rolId, privilegioIds);
        return ResponseEntity.ok().build();
    }
 
    @GetMapping("/{rolId}/privilegios")
    public ResponseEntity<List<PrivilegioDTO>> obtenerPrivilegiosPorRol(@PathVariable Long rolId) {
        return ResponseEntity.ok(roleService.obtenerPrivilegiosPorRol(rolId));
    }
}
