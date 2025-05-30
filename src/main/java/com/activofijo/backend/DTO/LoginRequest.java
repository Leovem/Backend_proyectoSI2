package com.activofijo.backend.dto;

import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;

public class LoginRequest {

    @NotBlank(message = "El usuario no puede estar vacío")
    private String usuario;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String contrasena;

    //@NotNull(message = "El id de la empresa no puede estar vacío")
    //private Long empresaId;

    public LoginRequest() {}

    public LoginRequest(String usuario, String contrasena, Long empresaId) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        //this.empresaId = empresaId;
    }

    // Getters y Setters

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /*public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }*/
}
