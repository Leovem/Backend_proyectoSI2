package com.activofijo.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private final String secret = "este_es_un_secreto_muy_largo_y_seguro_de_64_caracteres_para_garantizar_seguridad_1234567890";
    private final long expirationTime = 86400000; // 1 día en milisegundos
    private final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

    public String generateToken(String username, String role, Long empresaId) {
        logger.debug("Generando token para usuario: {}, rol: {}, empresaId: {}", username, role, empresaId);
        return Jwts.builder()
            .subject(username)
            .claim("authorities", List.of("ROLE_" + role))
            .claim("empresaId", empresaId)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(key)
            .compact();
    }

    public String getUsernameFromToken(String token) {
        try {
            logger.debug("🔑 JWT recibidoU: {}", token);
            Claims claims = extractAllClaims(token);
            String username = claims.getSubject();
            logger.debug("Usuario extraído del token: {}", username);
            return username;
        } catch (Exception e) {
            logger.error("Error al extraer el username del token: {}", e.getMessage());
            throw new RuntimeException("Error al extraer el username del token", e);
        }
    }

    public String getRoleFromToken(String token) {
        try {
            logger.debug("🔑 JWT recibidoR: {}", token);
            Claims claims = extractAllClaims(token);
            return claims.get("role", String.class);
        } catch (Exception e) {
            logger.error("Error al extraer el rol del token: {}", e.getMessage());
            throw new RuntimeException("Error al extraer el rol del token", e);
        }
    }

    public Long getEmpresaIdFromToken(String token) {
    try {
        logger.debug("🔑 JWT recibidoE: {}", token);
        logger.debug("[getEmpresaIdFromToken] token recibido = `{}` (length={})",
                 token, token != null ? token.length() : -1);
        Claims claims = extractAllClaims(token);
        Object value = claims.get("empresaId");

        if (value instanceof Integer) {
            return ((Integer) value).longValue();
        } else if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof String) {
            return Long.parseLong((String) value);
        } else {
            logger.error("❌ Tipo inesperado para empresaId: {}", value.getClass().getName());
            return null;
        }
    } catch (Exception e) {
        logger.error("❌ Error al extraer el empresaId del token: {}", e.getMessage());
        return null;
    }
    }



    public boolean validateToken(String token, String username) {
        try {
            logger.debug("🔑 JWT recibidoV: {}", token);
            Claims claims = extractAllClaims(token);
            boolean valid = claims.getSubject().equals(username) && !isTokenExpired(claims);
            logger.debug("Token validado: {}", valid);
            return valid;
        } catch (Exception e) {
            logger.error("Error al validar el token: {}", e.getMessage());
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        logger.debug("🔑 JWT recibidoEx: {}", token);
        logger.debug("[extractAllClaims] antes de parseClaimsJws, token = `{}` (length={})",
                 token, token != null ? token.length() : -1);
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}