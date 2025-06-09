package com.activofijo.backend.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class PlanDTO {

    private Long id;

    @NotBlank
    @Size(max = 50)
    private String nombre;

    private String descripcion;

    @DecimalMin("0.00")
    private BigDecimal precioMensual;

    @DecimalMin("0.00")
    private BigDecimal precioSemestral;

    @DecimalMin("0.00")
    private BigDecimal precioAnual;

    @Min(1)
    private int limiteUsuarios;

    private Integer limiteProyectos;

    private Integer limiteActivos;

    private boolean activo = true;

    // Getters y Setters
    // (puedo generarlos si quieres)
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
