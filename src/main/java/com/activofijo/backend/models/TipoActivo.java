package com.activofijo.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_activo")
public class TipoActivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    // Constructor vacío (requerido por JPA)
    public TipoActivo() {}

    // Constructor con campos
    public TipoActivo(String nombre) {
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
