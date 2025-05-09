package com.activofijo.backend.services;

import com.activofijo.backend.models.Usuario;
import com.activofijo.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.List;

//import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
    System.out.println("🔍 Cargando usuario desde UserDetailsService: " + usuario);
    Usuario user = usuarioRepository.findByUsuario(usuario)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    

    // Convertimos el rol a una autoridad de Spring Security
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRol().getNombre());

    return new User(user.getUsuario(), user.getContrasena(), List.of(authority));
}
}
