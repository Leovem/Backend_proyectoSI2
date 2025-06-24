package com.activofijo.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;


public class DepreciacionActivoCreateDTO {
    private Long activoId;
    private Long metodoDepreciacionId;
    private LocalDate fecha;
    private BigDecimal valor;
    private String moneda;
    // Getters y setters...
    public Long getActivoId() { return activoId; }
    public void setId(Long activoId) { this.activoId = activoId; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Long getMetodoDepreciacionId() { return metodoDepreciacionId; }
    public void setMetodoDepreciacionId(Long metodoDepreciacionId) { this.metodoDepreciacionId = metodoDepreciacionId; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
}