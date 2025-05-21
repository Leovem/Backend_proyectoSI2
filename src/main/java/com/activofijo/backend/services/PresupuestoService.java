package com.activofijo.backend.services;

import com.activofijo.backend.dto.PresupuestoCreateDTO;
import com.activofijo.backend.dto.PresupuestoDTO;
import com.activofijo.backend.models.*;
import com.activofijo.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PresupuestoService {

    private final PresupuestoRepository presupuestoRepository;
    private final MonedaRepository monedaRepository;
    private final DepartamentoRepository departamentoRepository;
    private final ProyectoRepository proyectoRepository;
    private final EmpresaRepository empresaRepository;

    public PresupuestoService(
        PresupuestoRepository presupuestoRepository,
        MonedaRepository monedaRepository,
        DepartamentoRepository departamentoRepository,
        ProyectoRepository proyectoRepository,
        EmpresaRepository empresaRepository
    ) {
        this.presupuestoRepository = presupuestoRepository;
        this.monedaRepository = monedaRepository;
        this.departamentoRepository = departamentoRepository;
        this.proyectoRepository = proyectoRepository;
        this.empresaRepository = empresaRepository;
    }

   @Transactional
public PresupuestoDTO crearPresupuesto(PresupuestoCreateDTO dto) {
    if (presupuestoRepository.existsByNombreAndEmpresa_Id(dto.getNombre(), dto.getEmpresaId())) {
        throw new IllegalArgumentException("Ya existe un presupuesto con ese nombre para esta empresa.");
    }

    Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
        .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada"));

    Moneda moneda = monedaRepository.findById(dto.getMoneda())
        .orElseThrow(() -> new IllegalArgumentException("Moneda no encontrada"));

    Departamento departamento = null;
    if (dto.getDepartamentoId() != null) {
        departamento = departamentoRepository.findById(dto.getDepartamentoId())
            .orElseThrow(() -> new IllegalArgumentException("Departamento no encontrado"));
    }

    Proyecto proyecto = null;
    if (dto.getProyectoId() != null) {
        proyecto = proyectoRepository.findById(dto.getProyectoId())
            .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));
    }

    // ✅ Crear la entidad
    Presupuesto presupuesto = new Presupuesto(
        dto.getNombre(),
        dto.getFechaInicio(),
        dto.getFechaFin(),
        dto.getMontoAsignado(),
        moneda,
        departamento,
        proyecto,
        empresa
    );
    Presupuesto guardado = presupuestoRepository.save(presupuesto);

    // ✅ Convertir y retornar como DTO
    return new PresupuestoDTO(
        guardado.getId(),
        guardado.getNombre(),
        guardado.getFechaInicio(),
        guardado.getFechaFin(),
        guardado.getMontoAsignado(),
        guardado.getMoneda().getCodigo(),
        guardado.getDepartamento() != null ? guardado.getDepartamento().getId() : null,
        guardado.getProyecto() != null ? guardado.getProyecto().getId() : null,
        guardado.getEmpresa().getId()
    );
}




    @Transactional(readOnly = true)
    public List<PresupuestoDTO> listarPorEmpresa(Long empresaId) {
        return presupuestoRepository.findAllByEmpresa_Id(empresaId).stream()
            .map(p -> new PresupuestoDTO(
                p.getId(),
                p.getNombre(),
                p.getFechaInicio(),
                p.getFechaFin(),
                p.getMontoAsignado(),
                p.getMoneda().getCodigo(),
                p.getDepartamento() != null ? p.getDepartamento().getId() : null,
                p.getProyecto() != null ? p.getProyecto().getId() : null,
                p.getEmpresa().getId()
            ))
            .collect(Collectors.toList());
    }

    @Transactional
    public PresupuestoDTO actualizarPresupuesto(Long id, PresupuestoCreateDTO dto) {
        Presupuesto presupuesto = presupuestoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Presupuesto no encontrado con id: " + id));

        if (!presupuesto.getEmpresa().getId().equals(dto.getEmpresaId())) {
            throw new IllegalArgumentException("No tiene permiso para modificar este presupuesto.");
        }

        if (!presupuesto.getNombre().equals(dto.getNombre()) &&
            presupuestoRepository.existsByNombreAndEmpresa_Id(dto.getNombre(), dto.getEmpresaId())) {
            throw new IllegalArgumentException("Ya existe un presupuesto con ese nombre para esta empresa.");
        }

        Moneda moneda = monedaRepository.findById(dto.getMoneda())
            .orElseThrow(() -> new IllegalArgumentException("Moneda no encontrada"));

        Departamento departamento = null;
        if (dto.getDepartamentoId() != null) {
            departamento = departamentoRepository.findById(dto.getDepartamentoId())
                .orElseThrow(() -> new IllegalArgumentException("Departamento no encontrado"));
        }

        Proyecto proyecto = null;
        if (dto.getProyectoId() != null) {
            proyecto = proyectoRepository.findById(dto.getProyectoId())
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));
        }

        presupuesto.setNombre(dto.getNombre());
        presupuesto.setFechaInicio(dto.getFechaInicio());
        presupuesto.setFechaFin(dto.getFechaFin());
        presupuesto.setMontoAsignado(dto.getMontoAsignado());
        presupuesto.setMoneda(moneda);
        presupuesto.setDepartamento(departamento);
        presupuesto.setProyecto(proyecto);

        Presupuesto actualizado = presupuestoRepository.save(presupuesto);

        return new PresupuestoDTO(
            actualizado.getId(),
            actualizado.getNombre(),
            actualizado.getFechaInicio(),
            actualizado.getFechaFin(),
            actualizado.getMontoAsignado(),
            actualizado.getMoneda().getCodigo(),
            actualizado.getDepartamento() != null ? actualizado.getDepartamento().getId() : null,
            actualizado.getProyecto() != null ? actualizado.getProyecto().getId() : null,
            actualizado.getEmpresa().getId()
        );
    }

    @Transactional
    public void eliminarPresupuesto(Long id, Long empresaId) {
        Presupuesto presupuesto = presupuestoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Presupuesto no encontrado con id: " + id));

        if (!presupuesto.getEmpresa().getId().equals(empresaId)) {
            throw new IllegalArgumentException("No tiene permiso para eliminar este presupuesto.");
        }

        presupuestoRepository.delete(presupuesto);
    }

    @Transactional(readOnly = true)
public PresupuestoDTO obtenerPorId(Long id, Long empresaId) {
    Presupuesto presupuesto = presupuestoRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Presupuesto no encontrado con id: " + id));

    if (!presupuesto.getEmpresa().getId().equals(empresaId)) {
        throw new IllegalArgumentException("No tiene acceso a este presupuesto.");
    }

    return new PresupuestoDTO(
        presupuesto.getId(),
        presupuesto.getNombre(),
        presupuesto.getFechaInicio(),
        presupuesto.getFechaFin(),
        presupuesto.getMontoAsignado(),
        presupuesto.getMoneda().getCodigo(),
        presupuesto.getDepartamento() != null ? presupuesto.getDepartamento().getId() : null,
        presupuesto.getProyecto() != null ? presupuesto.getProyecto().getId() : null,
        presupuesto.getEmpresa().getId()
    );
}

}
