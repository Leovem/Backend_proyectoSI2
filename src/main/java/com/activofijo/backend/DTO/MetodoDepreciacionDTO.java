package com.activofijo.backend.dto;


public class MetodoDepreciacionDTO {
    private Long id;
    private String nombre;
    private String formula;
    private Boolean requiereVidaUtil;

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
