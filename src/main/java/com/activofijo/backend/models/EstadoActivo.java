package com.activofijo.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "estados_activo")
public class EstadoActivo {

    @Id
    @Column(name = "estado", length = 50)
    private String estado;

    public EstadoActivo() {}

    public EstadoActivo(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}