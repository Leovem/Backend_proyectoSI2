package com.activofijo.backend.dto;


import java.math.BigDecimal;
import java.time.LocalDate;



public class TasaDepreciacionListDTO {
    private Long id;
    private String tipoActivoNombre;
    private BigDecimal porcentaje;
    private Integer vidaUtil;
    private LocalDate fechaVigencia;
    // Getters y setters...

     public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipoActivoNombre() { return tipoActivoNombre; }
    public void setTipoActivoNombre(String tipoActivoNombre) { this.tipoActivoNombre = tipoActivoNombre; }

    public BigDecimal getPorcentaje() { return porcentaje; }
    public void setPorcentaje(BigDecimal porcentaje) { this.porcentaje = porcentaje; }

    public Integer getVidaUtil() { return vidaUtil; }
    public void setVidaUtil(Integer vidaUtil) { this.vidaUtil = vidaUtil; }

    public LocalDate getFechaVigencia() { return fechaVigencia; }
    public void setFechaVigencia(LocalDate fechaVigencia) { this.fechaVigencia = fechaVigencia; }
}
