package com.activofijo.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "modelos", uniqueConstraints = @UniqueConstraint(name = "unq_modelo_marca", columnNames = {"nombre", "marca_id"}))
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id", foreignKey = @ForeignKey(name = "fk_marca", foreignKeyDefinition = "FOREIGN KEY (marca_id) REFERENCES marcas(id) ON DELETE CASCADE"))
    private Marca marca;

    public Modelo() {}

    public Modelo(String nombre, Marca marca) {
        this.nombre = nombre;
        this.marca = marca;
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

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
}