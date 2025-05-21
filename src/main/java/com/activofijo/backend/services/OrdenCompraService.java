package com.activofijo.backend.services;

import com.activofijo.backend.dto.OrdenCompraCreateDTO;
import com.activofijo.backend.dto.OrdenCompraDTO;
import com.activofijo.backend.models.*;
import com.activofijo.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdenCompraService {

    private final OrdenCompraRepository ordenCompraRepository;
    private final EmpresaRepository empresaRepository;
    private final ProveedorRepository proveedorRepository;
    private final UsuarioRepository usuarioRepository;
    private final PresupuestoRepository presupuestoRepository;

    public OrdenCompraService(OrdenCompraRepository ordenCompraRepository,
                               EmpresaRepository empresaRepository,
                               ProveedorRepository proveedorRepository,
                               UsuarioRepository usuarioRepository,
                               PresupuestoRepository presupuestoRepository) {
        this.ordenCompraRepository = ordenCompraRepository;
        this.empresaRepository = empresaRepository;
        this.proveedorRepository = proveedorRepository;
        this.usuarioRepository = usuarioRepository;
        this.presupuestoRepository = presupuestoRepository;
    }

    @Transactional
    public OrdenCompraDTO crearOrden(OrdenCompraCreateDTO dto) {
        if (ordenCompraRepository.existsByNumeroAndEmpresaId(dto.getNumero(), dto.getEmpresaId())) {
            throw new IllegalArgumentException("Ya existe una orden con ese número para esta empresa.");
        }

        Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada."));

        Proveedor proveedor = dto.getProveedorId() != null ?
                proveedorRepository.findById(dto.getProveedorId())
                        .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado.")) : null;

        Usuario usuario = dto.getUsuarioId() != null ?
                usuarioRepository.findById(dto.getUsuarioId())
                        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado.")) : null;

        Presupuesto presupuesto = dto.getPresupuestoId() != null ?
                presupuestoRepository.findById(dto.getPresupuestoId())
                        .orElseThrow(() -> new IllegalArgumentException("Presupuesto no encontrado.")) : null;

        OrdenCompra orden = new OrdenCompra(
                dto.getNumero(),
                dto.getFecha(),
                proveedor,
                usuario,
                presupuesto,
                empresa,
                EstadoOrdenCompra.valueOf(dto.getEstado()),
                dto.getObservaciones()
        );

        OrdenCompra saved = ordenCompraRepository.save(orden);

        return toDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<OrdenCompraDTO> listarPorEmpresa(Long empresaId) {
        return ordenCompraRepository.findAll().stream()
                .filter(o -> o.getEmpresa().getId().equals(empresaId))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrdenCompraDTO actualizarOrden(Long id, OrdenCompraCreateDTO dto) {
        OrdenCompra orden = ordenCompraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada con ID: " + id));

        if (!orden.getEmpresa().getId().equals(dto.getEmpresaId())) {
            throw new IllegalArgumentException("No tiene permiso para modificar esta orden.");
        }

        if (!orden.getNumero().equals(dto.getNumero()) &&
                ordenCompraRepository.existsByNumeroAndEmpresaId(dto.getNumero(), dto.getEmpresaId())) {
            throw new IllegalArgumentException("Ya existe una orden con ese número para esta empresa.");
        }

        orden.setNumero(dto.getNumero());
        orden.setFecha(dto.getFecha());
        orden.setEstado(EstadoOrdenCompra.valueOf(dto.getEstado()));
        orden.setObservaciones(dto.getObservaciones());

        if (dto.getProveedorId() != null) {
            orden.setProveedor(proveedorRepository.findById(dto.getProveedorId())
                    .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado")));
        } else {
            orden.setProveedor(null);
        }

        if (dto.getUsuarioId() != null) {
            orden.setUsuario(usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado")));
        } else {
            orden.setUsuario(null);
        }

        if (dto.getPresupuestoId() != null) {
            orden.setPresupuesto(presupuestoRepository.findById(dto.getPresupuestoId())
                    .orElseThrow(() -> new IllegalArgumentException("Presupuesto no encontrado")));
        } else {
            orden.setPresupuesto(null);
        }

        OrdenCompra updated = ordenCompraRepository.save(orden);

        return toDTO(updated);
    }

    @Transactional
    public void eliminarOrden(Long id, Long empresaId) {
        OrdenCompra orden = ordenCompraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada con id: " + id));

        if (!orden.getEmpresa().getId().equals(empresaId)) {
            throw new IllegalArgumentException("No tiene permiso para eliminar esta orden.");
        }

        ordenCompraRepository.delete(orden);
    }

    private OrdenCompraDTO toDTO(OrdenCompra oc) {
        OrdenCompraDTO dto = new OrdenCompraDTO();
        dto.setId(oc.getId());
        dto.setNumero(oc.getNumero());
        dto.setFecha(oc.getFecha());
        dto.setEmpresaId(oc.getEmpresa().getId());
        dto.setEstado(oc.getEstado().name());
        dto.setObservaciones(oc.getObservaciones());
        dto.setProveedorId(oc.getProveedor() != null ? oc.getProveedor().getId() : null);
        dto.setUsuarioId(oc.getUsuario() != null ? oc.getUsuario().getId() : null);
        dto.setPresupuestoId(oc.getPresupuesto() != null ? oc.getPresupuesto().getId() : null);
        return dto;
    }
}
