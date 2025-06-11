package com.activofijo.backend.services;

import com.activofijo.backend.models.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class AuditoriaService {

    private final JdbcTemplate jdbcTemplate;
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AuditoriaService.class);

    public AuditoriaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void registrarLoginExitoso(Long usuarioId, Long empresaId, String ipCliente) {
        String sql = """
                    SELECT registrar_auditoria(
                        CAST(? AS TEXT),       -- tabla_afectada
                        CAST(? AS INT),        -- registro_id
                        CAST(? AS TEXT),       -- operacion
                        CAST(? AS JSONB),      -- valores_anteriores
                        CAST(? AS JSONB),      -- valores_nuevos
                        CAST(? AS INT),        -- usuario_id
                        CAST(? AS INT),        -- empresa_id
                        CAST(? AS TEXT),       -- accion
                        CAST(? AS TEXT),       -- descripcion
                        CAST(? AS INET),       -- ip_cliente
                        CAST(? AS TIMESTAMP)   -- fecha_cliente
                    )
                """;

        jdbcTemplate.queryForObject(
                sql,
                Void.class,
                "usuarios", // p_tabla_afectada
                usuarioId.intValue(), // p_registro_id
                "LOGIN", // p_operacion
                null, // p_valores_anteriores
                null, // p_valores_nuevos
                usuarioId.intValue(), // p_usuario_id
                empresaId.intValue(), // p_empresa_id
                "Inicio de sesión", // p_accion
                "El usuario inició sesión correctamente.", // p_descripcion
                ipCliente, // p_ip_cliente
                Timestamp.from(Instant.now()) // p_fecha_cliente
        );
    }

    public void registrarLoginFallido(String usuario, String ipCliente, String motivo) {
        String sql = """
                    SELECT registrar_auditoria(
                        ?, ?, 'LOGIN', NULL, NULL,
                        NULL, NULL,
                        ?, ?,
                        ?, now()
                    )
                """;

        jdbcTemplate.queryForObject(
                sql,
                Void.class,
                "usuarios", // tabla_afectada
                -1, // registro_id (desconocido)
                "Intento fallido", // acción
                String.format("Intento fallido de login para usuario='%s'. Motivo: %s", usuario, motivo),
                ipCliente // IP cliente
        );
    }

    public void registrarCreacionUsuario(Usuario u, Long usuarioCreadorId, String ipCliente) {
        String sql = """
                    SELECT registrar_auditoria(
                        CAST(? AS TEXT),
                        CAST(? AS INT),
                        CAST(? AS TEXT),
                        CAST(? AS JSONB),
                        CAST(? AS JSONB),
                        CAST(? AS INT),
                        CAST(? AS INT),
                        CAST(? AS TEXT),
                        CAST(? AS TEXT),
                        CAST(? AS INET),
                        CAST(? AS TIMESTAMP)
                    )
                """;

        jdbcTemplate.queryForObject(
                sql,
                Void.class,
                "usuarios",
                u.getId().intValue(),
                "INSERT",
                null,
                toJson(u),
                usuarioCreadorId.intValue(), // 👈 el admin o usuario que hace el registro
                u.getEmpresa().getId().intValue(),
                "Creación",
                String.format("El usuario '%s' fue creado por el usuario ID=%d", u.getUsuario(), usuarioCreadorId),
                ipCliente,
                Timestamp.from(Instant.now()));
    }

    private String toJson(Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            return "{}";
        }
    }

    public void registrarOperacion(String tabla, String operacion,
                               Object antes, Object despues,
                               Long registroId, Long usuarioId, Long empresaId,
                               String accion, String descripcion, String ipCliente) {


    String sql = """
                    SELECT registrar_auditoria(
                        CAST(? AS TEXT),
                        CAST(? AS INT),
                        CAST(? AS TEXT),
                        CAST(? AS JSONB),
                        CAST(? AS JSONB),
                        CAST(? AS INT),
                        CAST(? AS INT),         
                        CAST(? AS TEXT),
                        CAST(? AS TEXT),
                        CAST(? AS INET),
                        CAST(? AS TIMESTAMP)
                    )
                """;

    try {
        jdbcTemplate.update(sql,
            tabla,
            registroId,
            operacion,
            toJson(antes),
            toJson(despues),
            usuarioId,
            empresaId,
            accion,
            descripcion,
            ipCliente, // ahora es TEXT, no necesita cast
            Timestamp.valueOf(LocalDateTime.now())
        );
    } catch (Exception e) {
        logger.warn("❌ Error registrando auditoría: {}", e.getMessage());
    }
}


}
