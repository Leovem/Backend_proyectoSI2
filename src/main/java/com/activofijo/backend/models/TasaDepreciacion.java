package com.activofijo.backend.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "tasa_depreciacion")
public class TasaDepreciacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tipo_activo_id", nullable = false)
    private TipoActivo tipoActivo;

    private BigDecimal porcentaje;

    private Integer vidaUtil;

    private LocalDate fechaVigencia = LocalDate.now();

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TipoActivo getTipoActivo() { return tipoActivo; }
    public void setTipoActivo(TipoActivo tipoActivo) { this.tipoActivo = tipoActivo; }

    public BigDecimal getPorcentaje() { return porcentaje; }
    public void setPorcentaje(BigDecimal porcentaje) { this.porcentaje = porcentaje; }

    public Integer getVidaUtil() { return vidaUtil; }
    public void setVidaUtil(Integer vidaUtil) { this.vidaUtil = vidaUtil; }

    public LocalDate getFechaVigencia() { return fechaVigencia; }
    public void setFechaVigencia(LocalDate fechaVigencia) { this.fechaVigencia = fechaVigencia; }
}