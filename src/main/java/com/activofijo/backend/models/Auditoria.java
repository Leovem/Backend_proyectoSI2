package com.activofijo.backend.models;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "auditoria")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tabla_afectada")
    private String tablaAfectada;

    @Column(name = "registro_id")
    private Long registroId;

    private String operacion;

    @Column(name = "valores_anteriores", columnDefinition = "jsonb")
    private String valoresAnteriores;

    @Column(name = "valores_nuevos", columnDefinition = "jsonb")
    private String valoresNuevos;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "empresa_id")
    private Long empresaId;

    private String accion;

    private String descripcion;

    @Column(name = "ip_cliente")
    private String ipCliente;

    @Column(name = "fecha_cliente")
    private OffsetDateTime fechaCliente;

    // Constructor vacío
    public Auditoria() {}

    // Constructor completo
    public Auditoria(Long id, String tablaAfectada, Long registroId, String operacion,
                     String valoresAnteriores, String valoresNuevos, Long usuarioId,
                     Long empresaId, String accion, String descripcion,
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

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTablaAfectada() {
        return tablaAfectada;
    }

    public void setTablaAfectada(String tablaAfectada) {
        this.tablaAfectada = tablaAfectada;
    }

    public Long getRegistroId() {
        return registroId;
    }

    public void setRegistroId(Long registroId) {
        this.registroId = registroId;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getValoresAnteriores() {
        return valoresAnteriores;
    }

    public void setValoresAnteriores(String valoresAnteriores) {
        this.valoresAnteriores = valoresAnteriores;
    }

    public String getValoresNuevos() {
        return valoresNuevos;
    }

    public void setValoresNuevos(String valoresNuevos) {
        this.valoresNuevos = valoresNuevos;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIpCliente() {
        return ipCliente;
    }

    public void setIpCliente(String ipCliente) {
        this.ipCliente = ipCliente;
    }

    public OffsetDateTime getFechaCliente() {
        return fechaCliente;
    }

    public void setFechaCliente(OffsetDateTime fechaCliente) {
        this.fechaCliente = fechaCliente;
    }
}
