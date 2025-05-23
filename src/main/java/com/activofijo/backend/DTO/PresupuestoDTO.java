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
    private String departamentoNombre;

    private Long proyectoId;
    private String proyectoNombre;

    private Long empresaId;

    // ✅ Constructor completo
    public PresupuestoDTO(
        Long id,
        String nombre,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        BigDecimal montoAsignado,
        String moneda,
        Long departamentoId,
        String departamentoNombre,
        Long proyectoId,
        String proyectoNombre,
        Long empresaId
    ) {
        this.id = id;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.montoAsignado = montoAsignado;
        this.moneda = moneda;
        this.departamentoId = departamentoId;
        this.departamentoNombre = departamentoNombre;
        this.proyectoId = proyectoId;
        this.proyectoNombre = proyectoNombre;
        this.empresaId = empresaId;
    }

    // ✅ Getters

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public BigDecimal getMontoAsignado() { return montoAsignado; }
    public String getMoneda() { return moneda; }

    public Long getDepartamentoId() { return departamentoId; }
    public String getDepartamentoNombre() { return departamentoNombre; }

    public Long getProyectoId() { return proyectoId; }
    public String getProyectoNombre() { return proyectoNombre; }

    public Long getEmpresaId() { return empresaId; }
}
