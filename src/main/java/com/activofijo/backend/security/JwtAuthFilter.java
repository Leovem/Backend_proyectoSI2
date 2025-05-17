package com.activofijo.backend.security;

import com.activofijo.backend.dto.UsuarioDTO;
import com.activofijo.backend.services.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    public JwtAuthFilter(JwtUtil jwtUtil,
            UsuarioService usuarioService) {
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // logger.info(">>> JwtAuthFilter: requestURI={},
        // method={}",request.getRequestURI(), request.getMethod());
        String authHeader = request.getHeader("Authorization");
        // logger.debug("[JwtAuthFilter] authHeader={}", authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            logger.debug("[JwtAuthFilter] token extraído={}", token);
            String username = null;

            try {
                username = jwtUtil.getUsernameFromToken(token);
                // logger.debug("Username extraído del token: {}", username);
            } catch (Exception e) {
                logger.warn("❌ Error al extraer username del token: {}", e.getMessage());
            }

            if (username != null
                    && SecurityContextHolder.getContext().getAuthentication() == null
                    && jwtUtil.validateToken(token, username)) {

                Long empresaId = jwtUtil.getEmpresaIdFromToken(token);
                logger.debug("[JwtAuthFilter] empresaId del token={}", empresaId);

                String role = jwtUtil.getRoleFromToken(token);
                logger.debug("[JwtAuthFilter] roles del token={}", role);
                UsuarioDTO dto = usuarioService.findByUsuario(username, empresaId);
                // logger.debug("[JwtAuthFilter] UsuarioDTO encontrado={}", dto);

                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + dto.getRolNombre());

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username,
                        token,
                        List.of(authority));
                SecurityContextHolder.getContext().setAuthentication(auth);
                logger.info("✅ Autenticación JWT exitosa para usuario: {}", username);
            }
        }

        filterChain.doFilter(request, response);
    }
}
