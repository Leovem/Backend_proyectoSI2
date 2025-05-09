package com.activofijo.backend.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    // Relación con Privilegio
    @ManyToMany
    @JoinTable(
            name = "rol_privilegio",
            joinColumns = @JoinColumn(name = "rol_id"),
            inverseJoinColumns = @JoinColumn(name = "privilegio_id")
    )
    private Set<Privilegio> privilegios = new HashSet<>();
    

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

    public Set<Privilegio> getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(Set<Privilegio> privilegios) {
        this.privilegios = privilegios;
    }
}
