package com.activofijo.backend.services;

import com.activofijo.backend.dto.MetodoDepreciacionCreateDTO;
import com.activofijo.backend.dto.MetodoDepreciacionDTO;
import com.activofijo.backend.models.MetodoDepreciacion;
import com.activofijo.backend.repository.MetodoDepreciacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetodoDepreciacionService {

    private final MetodoDepreciacionRepository repository;
    public MetodoDepreciacionService(MetodoDepreciacionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public MetodoDepreciacionDTO crear(MetodoDepreciacionCreateDTO dto) {
        if (repository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe un método de depreciación con ese nombre.");
        }

        MetodoDepreciacion metodo = new MetodoDepreciacion(
                dto.getNombre(),
                dto.getFormula(),
                dto.getRequiereVidaUtil()
        );

        return toDTO(repository.save(metodo));
    }

    @Transactional(readOnly = true)
    public List<MetodoDepreciacionDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public MetodoDepreciacionDTO actualizar(Long id, MetodoDepreciacionCreateDTO dto) {
        MetodoDepreciacion metodo = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Método de depreciación no encontrado."));

        if (!metodo.getNombre().equalsIgnoreCase(dto.getNombre()) &&
            repository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe otro método con ese nombre.");
        }

        metodo.setNombre(dto.getNombre());
        metodo.setFormula(dto.getFormula());
        metodo.setRequiereVidaUtil(dto.getRequiereVidaUtil());

        return toDTO(repository.save(metodo));
    }

    @Transactional
    public void eliminar(Long id) {
        MetodoDepreciacion metodo = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Método de depreciación no encontrado."));
        repository.delete(metodo);
    }

    private MetodoDepreciacionDTO toDTO(MetodoDepreciacion metodo) {
        MetodoDepreciacionDTO dto = new MetodoDepreciacionDTO();
        dto.setId(metodo.getId());
        dto.setNombre(metodo.getNombre());
        dto.setFormula(metodo.getFormula());
        dto.setRequiereVidaUtil(metodo.getRequiereVidaUtil());
        return dto;
    }
}
