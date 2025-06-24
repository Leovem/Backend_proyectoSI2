package com.activofijo.backend.dto;
import java.time.LocalDate;


class BajaActivoCreateDTO {
    private Long activoId;
    private LocalDate fecha;
    private String motivo;
    // Getters y setters...

    public Long getActivoId() { return activoId; }
    public void setActivoId(Long activoId) { this.activoId = activoId; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}