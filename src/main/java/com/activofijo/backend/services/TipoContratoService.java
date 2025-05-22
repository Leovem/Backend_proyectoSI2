package com.activofijo.backend.services;

import com.activofijo.backend.dto.TipoContratoCreateDTO;
import com.activofijo.backend.dto.TipoContratoDTO;
import com.activofijo.backend.models.TipoContrato;
import com.activofijo.backend.repository.TipoContratoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoContratoService {

    private final TipoContratoRepository repository;
     public TipoContratoService(TipoContratoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public TipoContratoDTO crear(TipoContratoCreateDTO dto) {
        if (repository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe un tipo de contrato con ese nombre.");
        }

        TipoContrato contrato = new TipoContrato(dto.getNombre());
        return toDTO(repository.save(contrato));
    }

    @Transactional(readOnly = true)
    public List<TipoContratoDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TipoContratoDTO actualizar(Long id, TipoContratoCreateDTO dto) {
        TipoContrato contrato = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de contrato no encontrado."));

        if (!contrato.getNombre().equalsIgnoreCase(dto.getNombre()) &&
            repository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe otro tipo de contrato con ese nombre.");
        }

        contrato.setNombre(dto.getNombre());
        return toDTO(repository.save(contrato));
    }

    @Transactional
    public void eliminar(Long id) {
        TipoContrato contrato = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de contrato no encontrado."));
        repository.delete(contrato);
    }

    private TipoContratoDTO toDTO(TipoContrato contrato) {
        TipoContratoDTO dto = new TipoContratoDTO();
        dto.setId(contrato.getId());
        dto.setNombre(contrato.getNombre());
        return dto;
    }
}
