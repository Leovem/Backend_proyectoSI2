package com.activofijo.backend.services;

import com.activofijo.backend.dto.*;
import com.activofijo.backend.models.*;
import com.activofijo.backend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<DepartamentoDTO> listarPorEmpresa(Long empresaId) {
        return departamentoRepository.findByEmpresaId(empresaId).stream()
            .map(dep -> new DepartamentoDTO(
                dep.getId(),
                dep.getNombre(),
                dep.getResponsable() != null ? dep.getResponsable().getId() : null,
                dep.getResponsable() != null ? dep.getResponsable().getNombreCompleto() : null,
                dep.getEmpresa().getId()
            )).toList();
    }

    public DepartamentoDTO obtenerPorId(Long id) {
        Departamento dep = departamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Departamento no encontrado"));
        return new DepartamentoDTO(
                dep.getId(),
                dep.getNombre(),
                dep.getResponsable() != null ? dep.getResponsable().getId() : null,
                dep.getResponsable() != null ? dep.getResponsable().getNombreCompleto() : null,
                dep.getEmpresa().getId()
        );
    }

    @Transactional
    public DepartamentoDTO crear(DepartamentoCreateDTO dto) {
        Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new EntityNotFoundException("Empresa no encontrada"));

        Usuario responsable = null;
        if (dto.getResponsableId() != null) {
            responsable = usuarioRepository.findById(dto.getResponsableId())
                    .orElseThrow(() -> new EntityNotFoundException("Responsable no encontrado"));
        }

        Departamento dep = new Departamento(dto.getNombre(), responsable, empresa);
        departamentoRepository.save(dep);

        return new DepartamentoDTO(dep.getId(), dep.getNombre(),
                responsable != null ? responsable.getId() : null,
                responsable != null ? responsable.getNombreCompleto() : null,
                empresa.getId());
    }

    @Transactional
    public DepartamentoDTO actualizar(Long id, DepartamentoUpdateDTO dto) {
        Departamento dep = departamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Departamento no encontrado"));

        dep.setNombre(dto.getNombre());

        if (dto.getResponsableId() != null) {
            Usuario responsable = usuarioRepository.findById(dto.getResponsableId())
                    .orElseThrow(() -> new EntityNotFoundException("Responsable no encontrado"));
            dep.setResponsable(responsable);
        } else {
            dep.setResponsable(null);
        }

        departamentoRepository.save(dep);

        return new DepartamentoDTO(dep.getId(), dep.getNombre(),
                dep.getResponsable() != null ? dep.getResponsable().getId() : null,
                dep.getResponsable() != null ? dep.getResponsable().getNombreCompleto() : null,
                dep.getEmpresa().getId());
    }

    public void eliminar(Long id) {
        if (!departamentoRepository.existsById(id)) {
            throw new EntityNotFoundException("Departamento no encontrado");
        }
        departamentoRepository.deleteById(id);
    }
}
