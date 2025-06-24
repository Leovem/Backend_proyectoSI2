package com.activofijo.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;



public class RevaluacionActivoCreateDTO {
    private Long activoId;
    private LocalDate fecha;
    private BigDecimal nuevoValor;
    private String motivo;
    // Getters y setters...

    public Long getActivoId() { return activoId; }
    public void setActivoId(Long activoId) { this.activoId = activoId; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public BigDecimal getNuevoValor() { return nuevoValor; }
    public void setNuevoValor(BigDecimal nuevoValor) { this.nuevoValor = nuevoValor; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}
