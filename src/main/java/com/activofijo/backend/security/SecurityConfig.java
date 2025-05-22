package com.activofijo.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          .csrf(csrf -> csrf.disable())
          .authorizeHttpRequests(auth -> auth
              .requestMatchers("/api/auth/**").permitAll()
              .requestMatchers("/api/privilegios/**").permitAll()
              //.requestMatchers("/api/usuarios/**").hasRole("ADMINISTRADOR")
              //.requestMatchers("/api/usuarios/**").permitAll()
              .requestMatchers("/api/usuarios/**").authenticated()
              .requestMatchers("/api/roles/**").authenticated()
              .requestMatchers("/api/proyectos/**").authenticated()
              .requestMatchers("/api/presupuestos/**").authenticated()
              .requestMatchers("/api/proveedores/**").authenticated()
              .requestMatchers("/api/ordenes-compra/**").authenticated()
              .requestMatchers("/api/detalles-orden/**").authenticated()
              .requestMatchers("/api/facturas/**").authenticated()
              .requestMatchers("/api/detalles-factura/**").authenticated()
              .requestMatchers("/api/cuentas-contables/**").authenticated()
              .requestMatchers("/api/activos/**").authenticated()
              .anyRequest().authenticated()
          )
          .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          // Insertamos nuestro filtro JWT antes que el filtro de Spring de login por formulario
          .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
