package com.activofijo.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmpresaCreateDTO {

    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(max = 100, message = "El nombre no debe exceder los 100 caracteres")
    private String nombre;

    @NotBlank(message = "El RFC es obligatorio")
    @Size(max = 13, message = "El RFC no debe exceder los 13 caracteres")
    private String rfc;

    // Constructor vacío
    public EmpresaCreateDTO() {}

    // Constructor con campos
    public EmpresaCreateDTO(String nombre, String rfc) {
        this.nombre = nombre;
        this.rfc = rfc;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }
}
