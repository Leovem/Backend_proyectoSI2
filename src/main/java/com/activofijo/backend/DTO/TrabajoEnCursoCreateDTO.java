package com.activofijo.backend.dto;

import java.time.LocalDate;


class TrabajoEnCursoCreateDTO {
    private Long proyectoId;
    private Long activoId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    // Getters y setters...

    public Long getProyectoId() { return proyectoId; }
    public void setProyectoId(Long proyectoId) { this.proyectoId = proyectoId; }

    public Long getActivoId() { return activoId; }
    public void setActivoId(Long activoId) { this.activoId = activoId; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
}