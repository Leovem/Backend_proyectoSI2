package com.activofijo.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;


@Entity
@Table(name = "planes")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    private String descripcion;

    @Column(name = "precio_mensual")
    @DecimalMin("0.00")
    private BigDecimal precioMensual;

    @Column(name = "precio_semestral")
    @DecimalMin("0.00")
    private BigDecimal precioSemestral;

    @Column(name = "precio_anual")
    @DecimalMin("0.00")
    private BigDecimal precioAnual;

    @Min(1)
    @Column(name = "limite_usuarios")
    private int limiteUsuarios;

    @Column(name = "limite_proyectos")
    private Integer limiteProyectos;

    @Column(name = "limite_activos")
    private Integer limiteActivos;

    private boolean activo = true;

    // Constructor vacío
    public Plan() {}

    // Getters y Setters manuales (puedo generártelos si quieres)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecioMensual() {
        return precioMensual;
    }

    public void setPrecioMensual(BigDecimal precioMensual) {
        this.precioMensual = precioMensual;
    }

    public BigDecimal getPrecioSemestral() {
        return precioSemestral;
    }

    public void setPrecioSemestral(BigDecimal precioSemestral) {
        this.precioSemestral = precioSemestral;
    }

    public BigDecimal getPrecioAnual() {
        return precioAnual;
    }

    public void setPrecioAnual(BigDecimal precioAnual) {
        this.precioAnual = precioAnual;
    }

    public int getLimiteUsuarios() {
        return limiteUsuarios;
    }

    public void setLimiteUsuarios(int limiteUsuarios) {
        this.limiteUsuarios = limiteUsuarios;
    }

    public Integer getLimiteProyectos() {
        return limiteProyectos;
    }

    public void setLimiteProyectos(Integer limiteProyectos) {
        this.limiteProyectos = limiteProyectos;
    }

    public Integer getLimiteActivos() {
        return limiteActivos;
    }

    public void setLimiteActivos(Integer limiteActivos) {
        this.limiteActivos = limiteActivos;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}