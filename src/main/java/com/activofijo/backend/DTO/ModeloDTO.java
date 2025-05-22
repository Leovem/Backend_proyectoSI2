package com.activofijo.backend.dto;


public class ModeloDTO {
    private Long id;
    private String nombre;
    private Long marcaId;
    private String marcaNombre;

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

    public Long getMarcaId() {
        return marcaId;
    }
    public void setMarcaId(Long marcaId) {
        this.marcaId = marcaId;
    }

    public String getMarcaNombre() {
        return marcaNombre;
    }
    public void setMarcaNombre(String marcaNombre) {
        this.marcaNombre = marcaNombre;
    }
}
