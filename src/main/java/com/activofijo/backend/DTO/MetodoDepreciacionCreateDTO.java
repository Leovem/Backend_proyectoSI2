package com.activofijo.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MetodoDepreciacionCreateDTO {

    @NotBlank
    private String nombre;

    @NotBlank
    private String formula;

    @NotNull
    private Boolean requiereVidaUtil;

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
