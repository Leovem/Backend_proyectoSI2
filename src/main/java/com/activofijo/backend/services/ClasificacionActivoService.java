package com.activofijo.backend.services;

import com.activofijo.backend.dto.ClasificacionActivoCreateDTO;
import com.activofijo.backend.dto.ClasificacionActivoDTO;
import com.activofijo.backend.models.ClasificacionActivo;
import com.activofijo.backend.repository.ClasificacionActivoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class ClasificacionActivoService {

    private final ClasificacionActivoRepository repository;
    public ClasificacionActivoService(ClasificacionActivoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ClasificacionActivoDTO crear(ClasificacionActivoCreateDTO dto) {
        if (repository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe una clasificación de activo con ese nombre.");
        }

        ClasificacionActivo clasificacion = new ClasificacionActivo(dto.getNombre());
        return toDTO(repository.save(clasificacion));
    }

    @Transactional(readOnly = true)
    public List<ClasificacionActivoDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ClasificacionActivoDTO actualizar(Long id, ClasificacionActivoCreateDTO dto) {
        ClasificacionActivo clasificacion = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Clasificación de activo no encontrada."));

        if (!clasificacion.getNombre().equalsIgnoreCase(dto.getNombre()) &&
            repository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe otra clasificación con ese nombre.");
        }

        clasificacion.setNombre(dto.getNombre());
        return toDTO(repository.save(clasificacion));
    }

    @Transactional
    public void eliminar(Long id) {
        ClasificacionActivo clasificacion = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Clasificación de activo no encontrada."));
        repository.delete(clasificacion);
    }

    private ClasificacionActivoDTO toDTO(ClasificacionActivo clasificacion) {
        ClasificacionActivoDTO dto = new ClasificacionActivoDTO();
        dto.setId(clasificacion.getId());
        dto.setNombre(clasificacion.getNombre());
        return dto;
    }
}
