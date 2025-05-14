package com.activofijo.backend.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para exponer datos básicos de un Privilegio.
 */
public class PrivilegioDTO {

    private Long id;

    @NotBlank(message = "El nombre del privilegio no puede estar vacío")
    private String nombre;

    public PrivilegioDTO() {}

    public PrivilegioDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
}
