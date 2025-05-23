package com.activofijo.backend.dto;

public class MonedaDTO {

    private String codigo;
    private String nombre;
    private Boolean activo;

    public MonedaDTO(String codigo, String nombre, Boolean activo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.activo = activo;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public Boolean getActivo() { return activo; }
}
