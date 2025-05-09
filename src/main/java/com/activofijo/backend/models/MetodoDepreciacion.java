package com.activofijo.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "metodo_depreciacion")
public class MetodoDepreciacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "formula", nullable = false)
    private String formula;

    @Column(name = "requiere_vida_util", nullable = false)
    private Boolean requiereVidaUtil;

    // Constructor vacío (requerido por JPA)
    public MetodoDepreciacion() {}

    // Constructor con campos
    public MetodoDepreciacion(String nombre, String formula, Boolean requiereVidaUtil) {
        this.nombre = nombre;
        this.formula = formula;
        this.requiereVidaUtil = requiereVidaUtil;
    }

    // Getters y Setters
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

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Boolean getRequiereVidaUtil() {
        return requiereVidaUtil;
    }

    public void setRequiereVidaUtil(Boolean requiereVidaUtil) {
        this.requiereVidaUtil = requiereVidaUtil;
    }
}
