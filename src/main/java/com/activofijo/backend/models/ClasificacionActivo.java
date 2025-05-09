package com.activofijo.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "clasificacion_activo")
public class ClasificacionActivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    // Constructor vacío (requerido por JPA)
    public ClasificacionActivo() {}

    // Constructor con campos
    public ClasificacionActivo(String nombre) {
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
