package com.activofijo.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class DepartamentoUpdateDTO {

    @NotBlank
    private String nombre;

    private Long responsableId;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Long getResponsableId() { return responsableId; }
    public void setResponsableId(Long responsableId) { this.responsableId = responsableId; }
}
