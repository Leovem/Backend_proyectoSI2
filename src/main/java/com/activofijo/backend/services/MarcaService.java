package com.activofijo.backend.services;

import com.activofijo.backend.dto.MarcaCreateDTO;
import com.activofijo.backend.dto.MarcaDTO;
import com.activofijo.backend.models.Marca;
import com.activofijo.backend.repository.MarcaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarcaService {

    private final MarcaRepository repository;
    public MarcaService(MarcaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public MarcaDTO crear(MarcaCreateDTO dto) {
        if (repository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe una marca con ese nombre.");
        }

        Marca marca = new Marca(dto.getNombre());
        return toDTO(repository.save(marca));
    }

    @Transactional(readOnly = true)
    public List<MarcaDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public MarcaDTO actualizar(Long id, MarcaCreateDTO dto) {
        Marca marca = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Marca no encontrada."));

        if (!marca.getNombre().equalsIgnoreCase(dto.getNombre()) &&
            repository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe otra marca con ese nombre.");
        }

        marca.setNombre(dto.getNombre());
        return toDTO(repository.save(marca));
    }

    @Transactional
    public void eliminar(Long id) {
        Marca marca = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Marca no encontrada."));
        repository.delete(marca);
    }

    private MarcaDTO toDTO(Marca marca) {
        MarcaDTO dto = new MarcaDTO();
        dto.setId(marca.getId());
        dto.setNombre(marca.getNombre());
        return dto;
    }
}
