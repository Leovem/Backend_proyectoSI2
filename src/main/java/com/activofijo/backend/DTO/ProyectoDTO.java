package com.activofijo.backend.dto;

public class ProyectoDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Long empresaId;  // <-- agregar

    public ProyectoDTO() {}

    // Constructor con empresaId
    public ProyectoDTO(Long id, String nombre, String descripcion, Long empresaId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.empresaId = empresaId;
    }

    // Getters y setters

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }
}
