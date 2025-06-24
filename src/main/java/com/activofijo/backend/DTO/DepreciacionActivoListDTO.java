package com.activofijo.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;


public class DepreciacionActivoListDTO {
    private Long id;
    private String activoNombre;
    private String metodoNombre;
    private LocalDate fecha;
    private BigDecimal valor;
    private String moneda;
    // Getters y setters...
     public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getActivoNombre() { return activoNombre; }
    public void setActivoNombre(String activoNombre) { this.activoNombre = activoNombre; }

    public String getMetodoNombre() { return metodoNombre; }
    public void setMetodoNombre(String metodoNombre) { this.metodoNombre = metodoNombre; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
}
