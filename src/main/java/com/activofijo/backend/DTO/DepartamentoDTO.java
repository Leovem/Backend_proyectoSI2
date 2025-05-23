package com.activofijo.backend.dto;

public class DepartamentoDTO {

    private Long id;
    private String nombre;
    private Long responsableId;
    private String responsableNombre;
    private Long empresaId;

    public DepartamentoDTO(Long id, String nombre, Long responsableId, String responsableNombre, Long empresaId) {
        this.id = id;
        this.nombre = nombre;
        this.responsableId = responsableId;
        this.responsableNombre = responsableNombre;
        this.empresaId = empresaId;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public Long getResponsableId() { return responsableId; }
    public String getResponsableNombre() { return responsableNombre; }
    public Long getEmpresaId() { return empresaId; }
}
