package com.activofijo.backend.DTO;

public class UsuarioDTO {

    private String usuario;
    private String nombreCompleto;
    private String email;
    private String contrasena;
    private Long rolId;
    private boolean activo;

    // Getters y Setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    public boolean isActivo() { 
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
