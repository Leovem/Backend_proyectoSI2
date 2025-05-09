package com.activofijo.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "modelos", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nombre", "marca_id"})
})
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne(optional = false)
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    // Constructor vacío
    public Modelo() {}

    // Constructor con campos
    public Modelo(String nombre, Marca marca) {
        this.nombre = nombre;
        this.marca = marca;
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

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
}
