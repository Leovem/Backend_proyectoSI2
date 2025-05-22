package com.activofijo.backend.services;

import com.activofijo.backend.dto.GrupoActivoCreateDTO;
import com.activofijo.backend.dto.GrupoActivoDTO;
import com.activofijo.backend.models.GrupoActivo;
import com.activofijo.backend.repository.GrupoActivoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GrupoActivoService {

    private final GrupoActivoRepository repository;
    public GrupoActivoService(GrupoActivoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public GrupoActivoDTO crear(GrupoActivoCreateDTO dto) {
        if (repository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe un grupo de activo con ese nombre.");
        }

        GrupoActivo grupo = new GrupoActivo(dto.getNombre());
        return toDTO(repository.save(grupo));
    }

    @Transactional(readOnly = true)
    public List<GrupoActivoDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public GrupoActivoDTO actualizar(Long id, GrupoActivoCreateDTO dto) {
        GrupoActivo grupo = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Grupo de activo no encontrado."));

        if (!grupo.getNombre().equalsIgnoreCase(dto.getNombre()) &&
            repository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe otro grupo con ese nombre.");
        }

        grupo.setNombre(dto.getNombre());
        return toDTO(repository.save(grupo));
    }

    @Transactional
    public void eliminar(Long id) {
        GrupoActivo grupo = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Grupo de activo no encontrado."));
        repository.delete(grupo);
    }

    private GrupoActivoDTO toDTO(GrupoActivo grupo) {
        GrupoActivoDTO dto = new GrupoActivoDTO();
        dto.setId(grupo.getId());
        dto.setNombre(grupo.getNombre());
        return dto;
    }
}
