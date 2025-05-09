package com.activofijo.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "monedas")
public class Moneda {

    @Id
    @Column(name = "codigo", length = 3)
    private String codigo;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo = true;

    // Constructor vacío
    public Moneda() {}

    // Constructor con campos
    public Moneda(String codigo, String nombre, Boolean activo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.activo = activo;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
