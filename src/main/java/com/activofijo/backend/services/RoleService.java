package com.activofijo.backend.services;

import com.activofijo.backend.DTO.PrivilegioDTO;
import com.activofijo.backend.DTO.RoleDTO;
import com.activofijo.backend.models.Privilegio;
import com.activofijo.backend.models.Rol;
import com.activofijo.backend.repository.PrivilegioRepository;
import com.activofijo.backend.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Set; 



@Service
public class RoleService {

    @Autowired
    private RolRepository roleRepository;

    @Autowired
    private PrivilegioRepository privilegioRepository;

    // Crear nuevo rol
    public RoleDTO crearRole(RoleDTO roleDTO) {
        Rol role = new Rol();
        role.setNombre(roleDTO.getNombre());
        role = roleRepository.save(role);

        roleDTO.setId(role.getId());
        return roleDTO;
    }

    // Obtener todos los roles
    public List<RoleDTO> obtenerTodos() {
        return roleRepository.findAll().stream()
                .map(role -> new RoleDTO(role.getId(), role.getNombre()))
                .collect(Collectors.toList());
    }

    // Obtener rol por ID
    public RoleDTO obtenerPorId(Long id) {
        Rol role = roleRepository.findById(id).orElseThrow();
        return new RoleDTO(role.getId(), role.getNombre());
    }

    // Actualizar rol
    public RoleDTO actualizarRole(Long id, RoleDTO roleDTO) {
        Rol role = roleRepository.findById(id).orElseThrow();
        role.setNombre(roleDTO.getNombre());
        role = roleRepository.save(role);
        return new RoleDTO(role.getId(), role.getNombre());
    }

    // Eliminar rol
    public void eliminarRole(Long id) {
        roleRepository.deleteById(id);
    }

    public void asignarPrivilegiosARol(Long rolId, List<Long> privilegioIds) {
    Rol rol = roleRepository.findById(rolId)
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

    // Convertir la lista de privilegios en un set
    Set<Privilegio> privilegios = new HashSet<>(privilegioRepository.findAllById(privilegioIds));
    rol.getPrivilegios().addAll(privilegios);  // Agregar los privilegios al rol
    roleRepository.save(rol);
}

    public List<PrivilegioDTO> obtenerPrivilegiosPorRol(Long rolId) {
        Rol rol = roleRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        return rol.getPrivilegios().stream()
                .map(p -> new PrivilegioDTO(p.getId(), p.getNombre()))
                .collect(Collectors.toList());
    }
    
}
