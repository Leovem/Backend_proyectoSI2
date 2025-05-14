package com.activofijo.backend.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles",
       uniqueConstraints = {
           @UniqueConstraint(name = "unq_rol_empresa", columnNames = {"nombre", "empresa_id"})
       }
)
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_roles_empresa",
                                         foreignKeyDefinition = "FOREIGN KEY (empresa_id) REFERENCES empresas(id) ON DELETE CASCADE"))
    private Empresa empresa;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rol_privilegio",
               joinColumns = @JoinColumn(name = "rol_id"),
               inverseJoinColumns = @JoinColumn(name = "privilegio_id"),
               foreignKey = @ForeignKey(name = "fk_rp_rol"),
               inverseForeignKey = @ForeignKey(name = "fk_rp_privilegio"))
    private Set<Privilegio> privilegios = new HashSet<>();

    // Constructors, getters, setters

    public Rol() {}

    public Rol(String nombre, Empresa empresa) {
        this.nombre = nombre;
        this.empresa = empresa;
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Set<Privilegio> getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(Set<Privilegio> privilegios) {
        this.privilegios = privilegios;
    }
}