package com.activofijo.backend.dto;

import jakarta.validation.constraints.*;

public class MonedaCreateDTO {

    @NotBlank
    @Size(min = 3, max = 3)
    private String codigo;

    @NotBlank
    @Size(max = 50)
    private String nombre;

    public String getCodigo() { return codigo.toUpperCase(); }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
