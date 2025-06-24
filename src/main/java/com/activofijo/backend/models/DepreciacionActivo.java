package com.activofijo.backend.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "depreciacion_activo")
public class DepreciacionActivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "activo_id", nullable = false)
    private Activo activo;

    @ManyToOne
    @JoinColumn(name = "metodo_depreciacion_id", nullable = false)
    private MetodoDepreciacion metodoDepreciacion;

    private BigDecimal valor;

    @Column(length = 3)
    private String moneda;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Activo getActivo() { return activo; }
    public void setActivo(Activo activo) { this.activo = activo; }

    public MetodoDepreciacion getMetodoDepreciacion() { return metodoDepreciacion; }
    public void setMetodoDepreciacion(MetodoDepreciacion metodoDepreciacion) { this.metodoDepreciacion = metodoDepreciacion; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
}