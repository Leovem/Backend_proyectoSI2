package com.activofijo.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "estados_activo")
public class EstadoActivo {

    @Id
    @Column(nullable = false, length = 50)
    private String estado;

    // Constructor vacío
    public EstadoActivo() {
    }

    // Constructor con el campo 'estado'
    public EstadoActivo(String estado) {
        this.estado = estado;
    }

    // Getter y Setter
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
