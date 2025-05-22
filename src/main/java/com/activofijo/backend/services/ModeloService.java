package com.activofijo.backend.services;

import com.activofijo.backend.dto.ModeloCreateDTO;
import com.activofijo.backend.dto.ModeloDTO;
import com.activofijo.backend.models.Marca;
import com.activofijo.backend.models.Modelo;
import com.activofijo.backend.repository.MarcaRepository;
import com.activofijo.backend.repository.ModeloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModeloService {

    private final ModeloRepository modeloRepository;
    private final MarcaRepository marcaRepository;
    public ModeloService(ModeloRepository modeloRepository, MarcaRepository marcaRepository) {
        this.modeloRepository = modeloRepository;
        this.marcaRepository = marcaRepository;
    }

    @Transactional
    public ModeloDTO crear(ModeloCreateDTO dto) {
        if (modeloRepository.existsByNombreIgnoreCaseAndMarca_Id(dto.getNombre(), dto.getMarcaId())) {
            throw new IllegalArgumentException("Ya existe un modelo con ese nombre para esta marca.");
        }

        Marca marca = marcaRepository.findById(dto.getMarcaId())
                .orElseThrow(() -> new IllegalArgumentException("Marca no encontrada."));

        Modelo modelo = new Modelo(dto.getNombre(), marca);
        return toDTO(modeloRepository.save(modelo));
    }

    @Transactional(readOnly = true)
    public List<ModeloDTO> listar() {
        return modeloRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ModeloDTO> listarPorMarca(Long marcaId) {
        return modeloRepository.findAllByMarca_Id(marcaId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ModeloDTO actualizar(Long id, ModeloCreateDTO dto) {
        Modelo modelo = modeloRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Modelo no encontrado."));

        if (!modelo.getMarca().getId().equals(dto.getMarcaId()) ||
            !modelo.getNombre().equalsIgnoreCase(dto.getNombre())) {
            if (modeloRepository.existsByNombreIgnoreCaseAndMarca_Id(dto.getNombre(), dto.getMarcaId())) {
                throw new IllegalArgumentException("Ya existe otro modelo con ese nombre para esta marca.");
            }
        }

        Marca marca = marcaRepository.findById(dto.getMarcaId())
                .orElseThrow(() -> new IllegalArgumentException("Marca no encontrada."));

        modelo.setNombre(dto.getNombre());
        modelo.setMarca(marca);

        return toDTO(modeloRepository.save(modelo));
    }

    @Transactional
    public void eliminar(Long id) {
        Modelo modelo = modeloRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Modelo no encontrado."));
        modeloRepository.delete(modelo);
    }

    private ModeloDTO toDTO(Modelo modelo) {
        ModeloDTO dto = new ModeloDTO();
        dto.setId(modelo.getId());
        dto.setNombre(modelo.getNombre());
        dto.setMarcaId(modelo.getMarca().getId());
        dto.setMarcaNombre(modelo.getMarca().getNombre());
        return dto;
    }
}
