package com.activofijo.backend.services;

import com.activofijo.backend.dto.TipoDepreciacionCreateDTO;
import com.activofijo.backend.dto.TipoDepreciacionDTO;
import com.activofijo.backend.models.TipoDepreciacion;
import com.activofijo.backend.repository.TipoDepreciacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoDepreciacionService {

    private final TipoDepreciacionRepository repository;
    public TipoDepreciacionService(TipoDepreciacionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public TipoDepreciacionDTO crear(TipoDepreciacionCreateDTO dto) {
        if (repository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe un tipo de depreciación con ese nombre.");
        }

        TipoDepreciacion tipo = new TipoDepreciacion(dto.getNombre());
        return toDTO(repository.save(tipo));
    }

    @Transactional(readOnly = true)
    public List<TipoDepreciacionDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TipoDepreciacionDTO actualizar(Long id, TipoDepreciacionCreateDTO dto) {
        TipoDepreciacion tipo = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de depreciación no encontrado."));

        if (!tipo.getNombre().equalsIgnoreCase(dto.getNombre()) &&
            repository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe otro tipo con ese nombre.");
        }

        tipo.setNombre(dto.getNombre());
        return toDTO(repository.save(tipo));
    }

    @Transactional
    public void eliminar(Long id) {
        TipoDepreciacion tipo = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de depreciación no encontrado."));
        repository.delete(tipo);
    }

    private TipoDepreciacionDTO toDTO(TipoDepreciacion tipo) {
        TipoDepreciacionDTO dto = new TipoDepreciacionDTO();
        dto.setId(tipo.getId());
        dto.setNombre(tipo.getNombre());
        return dto;
    }
}
