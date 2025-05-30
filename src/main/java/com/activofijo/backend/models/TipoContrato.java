package com.activofijo.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tipo_contrato", uniqueConstraints = @UniqueConstraint(name = "unq_nombre", columnNames = "nombre"))
public class TipoContrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "nombre", nullable = false, length = 100, unique = true)
    private String nombre;

    public TipoContrato() {}

    public TipoContrato(String nombre) {
        this.nombre = nombre;
    }

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