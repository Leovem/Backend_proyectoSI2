package com.activofijo.backend.services;

import com.activofijo.backend.DTO.PrivilegioDTO;
import com.activofijo.backend.models.Privilegio;
import com.activofijo.backend.repository.PrivilegioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrivilegioService {

    @Autowired
    private PrivilegioRepository privilegioRepository;

    // Crear nuevo privilegio
    public PrivilegioDTO crearPrivilegio(PrivilegioDTO privilegioDTO) {
        Privilegio privilegio = new Privilegio();
        privilegio.setNombre(privilegioDTO.getNombre());
        privilegio = privilegioRepository.save(privilegio);

        privilegioDTO.setId(privilegio.getId());
        return privilegioDTO;
    }

    // Obtener todos los privilegios
    public List<PrivilegioDTO> obtenerTodos() {
        return privilegioRepository.findAll().stream()
                .map(privilegio -> new PrivilegioDTO(privilegio.getId(), privilegio.getNombre()))
                .collect(Collectors.toList());
    }

    // Obtener privilegio por ID
    public PrivilegioDTO obtenerPorId(Long id) {
        Privilegio privilegio = privilegioRepository.findById(id).orElseThrow();
        return new PrivilegioDTO(privilegio.getId(), privilegio.getNombre());
    }

    // Eliminar privilegio
    public void eliminarPrivilegio(Long id) {
        privilegioRepository.deleteById(id);
    }
}
