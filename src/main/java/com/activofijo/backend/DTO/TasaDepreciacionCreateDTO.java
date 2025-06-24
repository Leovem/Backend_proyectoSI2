package com.activofijo.backend.dto;
import java.math.BigDecimal;
import java.time.LocalDate;


public class TasaDepreciacionCreateDTO {
    private Long tipoActivoId;
    private BigDecimal porcentaje;
    private Integer vidaUtil;
    private LocalDate fechaVigencia;
    // Getters y setters...
    public Long getTipoActivoId() { return tipoActivoId; }
    public void setTipoActivoId(Long tipoActivoId) { this.tipoActivoId = tipoActivoId; }

    public BigDecimal getPorcentaje() { return porcentaje; }
    public void setPorcentaje(BigDecimal porcentaje) { this.porcentaje = porcentaje; }

    public Integer getVidaUtil() { return vidaUtil; }
    public void setVidaUtil(Integer vidaUtil) { this.vidaUtil = vidaUtil; }

    public LocalDate getFechaVigencia() { return fechaVigencia; }
    public void setFechaVigencia(LocalDate fechaVigencia) { this.fechaVigencia = fechaVigencia; }
}
