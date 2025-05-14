package com.activofijo.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public class UsuarioDTO {

    private Long id;

    @NotBlank
    private String usuario;

    @NotBlank
    private String nombreCompleto;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private Long rolId;

    // Nuevo campo
    @NotBlank
    private String rolNombre;

    @NotNull
    private Long empresaId;

    private OffsetDateTime fechaUltimoAcceso;

    @NotNull
    private Boolean activo;

    public UsuarioDTO() {
    }

    // Añade rolNombre en el constructor si lo usas
    public UsuarioDTO(Long id, String usuario, String nombreCompleto, String email,
            Long rolId, String rolNombre, Long empresaId,
            OffsetDateTime fechaUltimoAcceso, Boolean activo) {
        this.id = id;
        this.usuario = usuario;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.rolId = rolId;
        this.rolNombre = rolNombre;
        this.empresaId = empresaId;
        this.fechaUltimoAcceso = fechaUltimoAcceso;
        this.activo = activo;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    // Nuevo getter y setter para rolNombre
    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public OffsetDateTime getFechaUltimoAcceso() {
        return fechaUltimoAcceso;
    }

    public void setFechaUltimoAcceso(OffsetDateTime fechaUltimoAcceso) {
        this.fechaUltimoAcceso = fechaUltimoAcceso;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", email='" + email + '\'' +
                ", rolId=" + rolId +
                ", rolNombre='" + rolNombre + '\'' +
                ", empresaId=" + empresaId +
                ", fechaUltimoAcceso=" + fechaUltimoAcceso +
                ", activo=" + activo +
                '}';
    }

}
