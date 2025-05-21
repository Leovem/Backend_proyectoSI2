package com.activofijo.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PresupuestoDTO {

    private Long id;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private BigDecimal montoAsignado;
    private String moneda;
    private Long departamentoId;
    private Long proyectoId;
    private Long empresaId;

    public PresupuestoDTO() {}

    public PresupuestoDTO(Long id, String nombre, LocalDate fechaInicio, LocalDate fechaFin,
                          BigDecimal montoAsignado, String moneda, Long departamentoId,
                          Long proyectoId, Long empresaId) {
        this.id = id;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.montoAsignado = montoAsignado;
        this.moneda = moneda;
        this.departamentoId = departamentoId;
        this.proyectoId = proyectoId;
        this.empresaId = empresaId;
    }

    // Getters y setters
    public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getNombre() {
    return nombre;
}

public void setNombre(String nombre) {
    this.nombre = nombre;
}

public LocalDate getFechaInicio() {
    return fechaInicio;
}

public void setFechaInicio(LocalDate fechaInicio) {
    this.fechaInicio = fechaInicio;
}

public LocalDate getFechaFin() {
    return fechaFin;
}

public void setFechaFin(LocalDate fechaFin) {
    this.fechaFin = fechaFin;
}

public BigDecimal getMontoAsignado() {
    return montoAsignado;
}

public void setMontoAsignado(BigDecimal montoAsignado) {
    this.montoAsignado = montoAsignado;
}

public String getMoneda() {
    return moneda;
}

public void setMoneda(String moneda) {
    this.moneda = moneda;
}

public Long getDepartamentoId() {
    return departamentoId;
}

public void setDepartamentoId(Long departamentoId) {
    this.departamentoId = departamentoId;
}

public Long getProyectoId() {
    return proyectoId;
}

public void setProyectoId(Long proyectoId) {
    this.proyectoId = proyectoId;
}

public Long getEmpresaId() {
    return empresaId;
}

public void setEmpresaId(Long empresaId) {
    this.empresaId = empresaId;
}

}
