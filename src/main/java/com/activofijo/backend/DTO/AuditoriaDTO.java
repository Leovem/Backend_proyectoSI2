package com.activofijo.backend.dto;

import java.time.OffsetDateTime;

public class AuditoriaDTO {

    private Long id;
    private String tablaAfectada;
    private Long registroId;
    private String operacion;
    private String valoresAnteriores;
    private String valoresNuevos;
    private Long usuarioId;
    private Long empresaId;
    private String accion;
    private String descripcion;
    private String ipCliente;
    private OffsetDateTime fechaCliente;

    // Constructor completo
    public AuditoriaDTO(Long id, String tablaAfectada, Long registroId, String operacion,
                        String valoresAnteriores, String valoresNuevos,
                        Long usuarioId, Long empresaId,
                        String accion, String descripcion,
                        String ipCliente, OffsetDateTime fechaCliente) {
        this.id = id;
        this.tablaAfectada = tablaAfectada;
        this.registroId = registroId;
        this.operacion = operacion;
        this.valoresAnteriores = valoresAnteriores;
        this.valoresNuevos = valoresNuevos;
        this.usuarioId = usuarioId;
        this.empresaId = empresaId;
        this.accion = accion;
        this.descripcion = descripcion;
        this.ipCliente = ipCliente;
        this.fechaCliente = fechaCliente;
    }

    // Getters (puedes agregar @JsonProperty si necesitas cambiar el nombre en JSON)
    public Long getId() {
        return id;
    }

    public String getTablaAfectada() {
        return tablaAfectada;
    }

    public Long getRegistroId() {
        return registroId;
    }

    public String getOperacion() {
        return operacion;
    }

    public String getValoresAnteriores() {
        return valoresAnteriores;
    }

    public String getValoresNuevos() {
        return valoresNuevos;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public String getAccion() {
        return accion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getIpCliente() {
        return ipCliente;
    }

    public OffsetDateTime getFechaCliente() {
        return fechaCliente;
    }
}
