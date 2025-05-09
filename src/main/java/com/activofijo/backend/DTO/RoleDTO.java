package com.activofijo.backend.DTO;
import java.util.Set;

public class RoleDTO {
    private Long id;
    private String nombre;
    private Set<Long> privilegioIds;

    // Constructor personalizado requerido
    public RoleDTO(Long id, String nombre, Set<Long>privilegioIds) {
        this.id = id;
        this.nombre = nombre;
        this.privilegioIds = privilegioIds;
    }

    public RoleDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public RoleDTO() {}
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

    public Set<Long> getPrivilegioIds() {
        return privilegioIds;
    }

    public void setPrivilegioIds(Set<Long> privilegioIds) {
        this.privilegioIds = privilegioIds;
    }
}
