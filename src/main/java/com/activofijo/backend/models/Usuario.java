package com.activofijo.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "usuarios",
       uniqueConstraints = {
         @UniqueConstraint(name = "unq_usuario_empresa", columnNames = {"usuario", "empresa_id"}),
         @UniqueConstraint(name = "unq_email_empresa",   columnNames = {"email",   "empresa_id"})
       }
)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "usuario", nullable = false, length = 100)
    private String usuario;

    @NotBlank
    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
             message = "Email inválido")
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @NotBlank
    @Column(name = "contrasena", nullable = false, columnDefinition = "TEXT")
    private String contrasena;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rol_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "fk_rol"))
    private Rol rol;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "fk_empresa"))
    private Empresa empresa;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private OffsetDateTime fechaCreacion;

    @Column(name = "fecha_ultimo_acceso")
    private OffsetDateTime fechaUltimoAcceso;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    // --------------------
    // Getters y Setters
    // --------------------

    public Long getId() {
        return id;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public OffsetDateTime getFechaCreacion() {
        return fechaCreacion;
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

    public String toString() {
        return "Usuario{" +
               "id=" + id +
               ", usuario='" + usuario + '\'' +
               ", nombreCompleto='" + nombreCompleto + '\'' +
               ", email='" + email + '\'' +
               ", rol=" + (rol != null ? rol.getNombre() : "null") +
               ", empresaId=" + (empresa != null ? empresa.getId() : "null") +
               ", fechaCreacion=" + fechaCreacion +
               ", fechaUltimoAcceso=" + fechaUltimoAcceso +
               ", activo=" + activo +
               '}';
    }
}
