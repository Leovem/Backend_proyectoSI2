package com.activofijo.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "ubicacion")
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "responsable_id")
    private Usuario responsable;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    // Constructor vacío
    public Ubicacion() {}

    // Constructor con campos
    public Ubicacion(String nombre, String direccion, Usuario responsable, Empresa empresa) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.responsable = responsable;
        this.empresa = empresa;
    }

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Usuario getResponsable() {
        return responsable;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
