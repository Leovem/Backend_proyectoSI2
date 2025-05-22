package com.activofijo.backend.services;

import com.activofijo.backend.dto.TipoActivoCreateDTO;
import com.activofijo.backend.dto.TipoActivoDTO;
import com.activofijo.backend.models.TipoActivo;
import com.activofijo.backend.repository.TipoActivoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoActivoService {

    private final TipoActivoRepository tipoActivoRepository;
    public TipoActivoService(TipoActivoRepository tipoActivoRepository) {
        this.tipoActivoRepository = tipoActivoRepository;
    }

    @Transactional
    public TipoActivoDTO crear(TipoActivoCreateDTO dto) {
        if (tipoActivoRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe un tipo de activo con ese nombre.");
        }

        TipoActivo tipoActivo = new TipoActivo();
        tipoActivo.setNombre(dto.getNombre());

        TipoActivo guardado = tipoActivoRepository.save(tipoActivo);
        return toDTO(guardado);
    }

    @Transactional(readOnly = true)
    public List<TipoActivoDTO> listar() {
        return tipoActivoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TipoActivoDTO actualizar(Long id, TipoActivoCreateDTO dto) {
        TipoActivo tipoActivo = tipoActivoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TipoActivo no encontrado."));

        // Evitar duplicado de nombre (ignorando mayúsculas)
        if (!tipoActivo.getNombre().equalsIgnoreCase(dto.getNombre()) &&
            tipoActivoRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe otro tipo de activo con ese nombre.");
        }

        tipoActivo.setNombre(dto.getNombre());
        TipoActivo actualizado = tipoActivoRepository.save(tipoActivo);
        return toDTO(actualizado);
    }

    @Transactional
    public void eliminar(Long id) {
        TipoActivo tipoActivo = tipoActivoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TipoActivo no encontrado."));
        tipoActivoRepository.delete(tipoActivo);
    }

    private TipoActivoDTO toDTO(TipoActivo entity) {
        TipoActivoDTO dto = new TipoActivoDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        return dto;
    }
}
