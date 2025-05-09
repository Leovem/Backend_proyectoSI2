package com.activofijo.backend.DTO;

public class PrivilegioDTO {

    private Long id;
    private String nombre;

    // ✅ Constructor con parámetros
    public PrivilegioDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // ✅ Constructor vacío (opcional pero útil)
    public PrivilegioDTO() {}

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
}
