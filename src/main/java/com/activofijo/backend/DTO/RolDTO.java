package com.activofijo.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * DTO para exponer datos básicos de un Rol.
 */
public class RolDTO {

    private Long id;

    @NotBlank(message = "El nombre del rol no puede estar vacío")
    private String nombre;

    @NotNull(message = "Debe especificar el id de la empresa asociada")
    private Long empresaId;

    private List<Long> privilegioIds;
    private List<PrivilegioDTO> privilegios;


    public RolDTO() {}

    public RolDTO(Long id, String nombre, Long empresaId) {
        this.id = id;
        this.nombre = nombre;
        this.empresaId = empresaId;
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

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public List<Long> getPrivilegioIds() {
        return privilegioIds;
    }

    public void setPrivilegioIds(List<Long> privilegioIds) {
        this.privilegioIds = privilegioIds;
    }

    public List<PrivilegioDTO> getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(List<PrivilegioDTO> privilegios) {
        this.privilegios = privilegios;
    }
}
