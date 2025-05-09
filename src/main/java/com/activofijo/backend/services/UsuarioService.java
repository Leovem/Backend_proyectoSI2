package com.activofijo.backend.services;

import com.activofijo.backend.DTO.UsuarioDTO;
import com.activofijo.backend.models.Rol;
import com.activofijo.backend.models.Usuario;
import com.activofijo.backend.repository.UsuarioRepository;
import com.activofijo.backend.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolRepository rolRepository;

    public Usuario registrarUsuario(Usuario usuario) {
        // Encriptar contraseña
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    public Usuario crearUsuario(UsuarioDTO dto) throws Exception {
        if (usuarioRepository.findByUsuario(dto.getUsuario()).isPresent()) {
            throw new Exception("El nombre de usuario ya existe.");
        }

        if (dto.getEmail() != null && usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new Exception("El email ya está registrado.");
        }

        Rol rol = rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new Exception("Rol no encontrado"));

        Usuario usuario = new Usuario();
        usuario.setUsuario(dto.getUsuario());
        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setEmail(dto.getEmail());
        usuario.setContrasena(dto.getContrasena()); // Aquí podrías usar BCrypt
        usuario.setRol(rol);
        usuario.setFechaCreacion(LocalDateTime.now());
        usuario.setActivo(true);

        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(Long id, Usuario datosActualizados) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    
        usuario.setNombreCompleto(datosActualizados.getNombreCompleto());
        usuario.setUsuario(datosActualizados.getUsuario());
        usuario.setEmail(datosActualizados.getEmail());
        usuario.setRol(datosActualizados.getRol());
        
        
        // Si deseas permitir actualizar contraseña (opcional y con encoding):
        if (datosActualizados.getContrasena() != null && !datosActualizados.getContrasena().isEmpty()) {
            usuario.setContrasena(passwordEncoder.encode(datosActualizados.getContrasena()));
        }
    
        return usuarioRepository.save(usuario);
    }
    

    public Optional<Usuario> buscarPorUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAllByOrderByIdAsc();  // cambio realizado para devolver los usuarios en orden
    }

    

    public boolean existePorUsuario(String usuario) {
        return usuarioRepository.existsByUsuario(usuario);
    }

    public boolean existePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    // Desactivar y activar usuarios
public Usuario desactivarUsuario(Long id) {
    Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    usuario.setActivo(false);
    return usuarioRepository.save(usuario);
}

public Usuario activarUsuario(Long id) {
    Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    usuario.setActivo(true);
    return usuarioRepository.save(usuario);
}

public List<Usuario> listarActivos() {
    return usuarioRepository.findByActivoTrue();
}

public Usuario obtenerPorId(Long id) {
    return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
}


}
