package com.activofijo.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults()) // ✅ Lambda DSL para CORS
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/api/auth/register").authenticated()
                .requestMatchers("/api/privilegios/**").permitAll()
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
                .requestMatchers("/api/clasificacion-activo/**").permitAll()
                .requestMatchers("/api/tipo-activo/**").permitAll()
                .requestMatchers("/api/tipo-contrato/**").permitAll()
                .requestMatchers("/api/ubicaciones/**").authenticated()
                .requestMatchers("/api/departamentos/**").authenticated()
                .requestMatchers("/api/grupo-activo/**").permitAll()
                .requestMatchers("/api/marcas/**").permitAll()
                .requestMatchers("/api/metodo-depreciacion/**").permitAll()
                .requestMatchers("/api/tipo-depreciacion/**").permitAll()
                .requestMatchers("/api/modelos/**").permitAll()
                .requestMatchers("/api/monedas/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ✅ Bean para CORS moderno
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000", "https://frontend-proyecto-si-2-nrw3.vercel.app"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
