package com.activofijo.backend.models;


import jakarta.persistence.*;

@Entity
@Table(name = "vehiculos", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"placa", "empresa_id"})
})
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "activo_id", nullable = false, unique = true)
    private Activo activo;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    private String placa;
    private String motor;
    private String chasis;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Activo getActivo() { return activo; }
    public void setActivo(Activo activo) { this.activo = activo; }

    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getMotor() { return motor; }
    public void setMotor(String motor) { this.motor = motor; }

    public String getChasis() { return chasis; }
    public void setChasis(String chasis) { this.chasis = chasis; }
}
