package com.activofijo.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_contrato")
public class TipoContrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    // Constructor vacío
    public TipoContrato() {}

    // Constructor con campo
    public TipoContrato(String nombre) {
        this.nombre = nombre;
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
}
