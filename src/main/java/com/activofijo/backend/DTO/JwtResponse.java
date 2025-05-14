package com.activofijo.backend.dto;

public class JwtResponse {

    private String token;
    private String usuario;
    private String nombreCompleto;
    private String rol;
    private Long empresaId;

    public JwtResponse(String token, String usuario, String nombreCompleto, String rol, Long empresaId) {
        this.token = token;
        this.usuario = usuario;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
        this.empresaId = empresaId;
    }

    // Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }
}