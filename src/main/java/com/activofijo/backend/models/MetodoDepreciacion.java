package com.activofijo.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "metodo_depreciacion", uniqueConstraints = @UniqueConstraint(name = "unq_nombre", columnNames = "nombre"))
public class MetodoDepreciacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "nombre", nullable = false, length = 100, unique = true)
    private String nombre;

    @NotBlank
    @Column(name = "formula", nullable = false, columnDefinition = "TEXT")
    private String formula;

    @Column(name = "requiere_vida_util", nullable = false)
    private Boolean requiereVidaUtil = false;

    public MetodoDepreciacion() {}

    public MetodoDepreciacion(String nombre, String formula, Boolean requiereVidaUtil) {
        this.nombre = nombre;
        this.formula = formula;
        this.requiereVidaUtil = requiereVidaUtil;
    }

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