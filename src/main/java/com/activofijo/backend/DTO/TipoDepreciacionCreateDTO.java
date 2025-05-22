package com.activofijo.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TipoDepreciacionCreateDTO {
    @NotBlank
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
