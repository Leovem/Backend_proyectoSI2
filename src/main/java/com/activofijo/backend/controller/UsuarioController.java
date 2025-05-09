package com.activofijo.backend.controller;

import com.activofijo.backend.models.Usuario;
import com.activofijo.backend.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }
    // ✅ Obtener usuarios activos
    @GetMapping("/activos")
    public ResponseEntity<List<Usuario>> listarActivos() {
        return ResponseEntity.ok(usuarioService.listarActivos());
    }

    // ✅ Buscar usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    // ✅ Actualizar datos de usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Usuario actualizado = usuarioService.actualizarUsuario(id, usuarioActualizado);
        return ResponseEntity.ok(actualizado);
    }

    // ✅ Desactivar usuario (soft delete)
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Usuario> desactivarUsuario(@PathVariable Long id) {
        System.out.println("Llegó al método desactivarUsuario con id: " + id);
        Usuario usuario = usuarioService.desactivarUsuario(id);
        return ResponseEntity.ok(usuario);
    }

    // ✅ Activar usuario
    @PatchMapping("/{id}/activar")
    public ResponseEntity<Usuario> activarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.activarUsuario(id);
        return ResponseEntity.ok(usuario);
    }
}
