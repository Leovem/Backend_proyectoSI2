package com.activofijo.backend.services;

import com.activofijo.backend.dto.UbicacionCreateDTO;
import com.activofijo.backend.dto.UbicacionDTO;
import com.activofijo.backend.models.Empresa;
import com.activofijo.backend.models.Ubicacion;
import com.activofijo.backend.models.Usuario;
import com.activofijo.backend.repository.EmpresaRepository;
import com.activofijo.backend.repository.UbicacionRepository;
import com.activofijo.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UbicacionService {

    private final UbicacionRepository ubicacionRepository;
    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;
    public UbicacionService(
        UbicacionRepository ubicacionRepository,
        EmpresaRepository empresaRepository,
        UsuarioRepository usuarioRepository
    ) {
        this.ubicacionRepository = ubicacionRepository;
        this.empresaRepository = empresaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public UbicacionDTO crear(UbicacionCreateDTO dto) {
        if (ubicacionRepository.existsByNombreIgnoreCaseAndEmpresa_Id(dto.getNombre(), dto.getEmpresaId())) {
            throw new IllegalArgumentException("Ya existe una ubicación con ese nombre en la empresa.");
        }

        Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada."));

        Usuario responsable = null;
        if (dto.getResponsableId() != null) {
            responsable = usuarioRepository.findById(dto.getResponsableId())
                    .orElseThrow(() -> new IllegalArgumentException("Responsable no encontrado."));
        }

        Ubicacion ubicacion = new Ubicacion(dto.getNombre(), dto.getDireccion(), responsable, empresa);
        return toDTO(ubicacionRepository.save(ubicacion));
    }

    @Transactional(readOnly = true)
    public List<UbicacionDTO> listarPorEmpresa(Long empresaId) {
        return ubicacionRepository.findAllByEmpresa_Id(empresaId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UbicacionDTO actualizar(Long id, UbicacionCreateDTO dto) {
        Ubicacion ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ubicación no encontrada."));

        if (!ubicacion.getEmpresa().getId().equals(dto.getEmpresaId())) {
            throw new IllegalArgumentException("No tiene permiso para modificar esta ubicación.");
        }

        if (!ubicacion.getNombre().equalsIgnoreCase(dto.getNombre()) &&
            ubicacionRepository.existsByNombreIgnoreCaseAndEmpresa_Id(dto.getNombre(), dto.getEmpresaId())) {
            throw new IllegalArgumentException("Ya existe otra ubicación con ese nombre en la empresa.");
        }

        Usuario responsable = null;
        if (dto.getResponsableId() != null) {
            responsable = usuarioRepository.findById(dto.getResponsableId())
                    .orElseThrow(() -> new IllegalArgumentException("Responsable no encontrado."));
        }

        ubicacion.setNombre(dto.getNombre());
        ubicacion.setDireccion(dto.getDireccion());
        ubicacion.setResponsable(responsable);

        return toDTO(ubicacionRepository.save(ubicacion));
    }

    @Transactional
    public void eliminar(Long id, Long empresaId) {
        Ubicacion ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ubicación no encontrada."));

        if (!ubicacion.getEmpresa().getId().equals(empresaId)) {
            throw new IllegalArgumentException("No tiene permiso para eliminar esta ubicación.");
        }

        ubicacionRepository.delete(ubicacion);
    }

    private UbicacionDTO toDTO(Ubicacion ubicacion) {
        UbicacionDTO dto = new UbicacionDTO();
        dto.setId(ubicacion.getId());
        dto.setNombre(ubicacion.getNombre());
        dto.setDireccion(ubicacion.getDireccion());
        dto.setEmpresaId(ubicacion.getEmpresa().getId());

        if (ubicacion.getResponsable() != null) {
            dto.setResponsableId(ubicacion.getResponsable().getId());
            dto.setResponsableNombre(ubicacion.getResponsable().getNombreCompleto());
        }

        return dto;
    }
}
