package com.activofijo.backend.dto;

import java.time.LocalDate;


class BajaActivoListDTO {
    private Long id;
    private String activoNombre;
    private LocalDate fecha;
    private String motivo;
    // Getters y setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getActivoNombre() { return activoNombre; }
    public void setActivoNombre(String activoNombre) { this.activoNombre = activoNombre; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}