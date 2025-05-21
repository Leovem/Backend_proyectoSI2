package com.activofijo.backend.services;

import com.activofijo.backend.dto.FacturaCreateDTO;
import com.activofijo.backend.dto.FacturaDTO;
import com.activofijo.backend.models.*;
import com.activofijo.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final EmpresaRepository empresaRepository;
    private final ProveedorRepository proveedorRepository;
    private final UsuarioRepository usuarioRepository;
    private final OrdenCompraRepository ordenCompraRepository;
    private final PresupuestoRepository presupuestoRepository;
    private final CuentaContableRepository cuentaContableRepository;
    private final MonedaRepository monedaRepository;

    public FacturaService(FacturaRepository facturaRepository,
                          EmpresaRepository empresaRepository,
                          ProveedorRepository proveedorRepository,
                          UsuarioRepository usuarioRepository,
                          OrdenCompraRepository ordenCompraRepository,
                          PresupuestoRepository presupuestoRepository,
                          CuentaContableRepository cuentaContableRepository,
                          MonedaRepository monedaRepository) {
        this.facturaRepository = facturaRepository;
        this.empresaRepository = empresaRepository;
        this.proveedorRepository = proveedorRepository;
        this.usuarioRepository = usuarioRepository;
        this.ordenCompraRepository = ordenCompraRepository;
        this.presupuestoRepository = presupuestoRepository;
        this.cuentaContableRepository = cuentaContableRepository;
        this.monedaRepository = monedaRepository;
    }

    @Transactional
    public FacturaDTO crearFactura(FacturaCreateDTO dto) {
        if (facturaRepository.existsByNumeroAndEmpresa_Id(dto.getNumero(), dto.getEmpresaId())) {
            throw new IllegalArgumentException("Ya existe una factura con ese número para esta empresa.");
        }

        Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada"));

        Proveedor proveedor = dto.getProveedorId() != null
                ? proveedorRepository.findById(dto.getProveedorId())
                    .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"))
                : null;

        Usuario usuario = dto.getUsuarioId() != null
                ? usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"))
                : null;

        OrdenCompra ordenCompra = dto.getOrdenCompraId() != null
                ? ordenCompraRepository.findById(dto.getOrdenCompraId())
                    .orElseThrow(() -> new IllegalArgumentException("Orden de compra no encontrada"))
                : null;

        Presupuesto presupuesto = dto.getPresupuestoId() != null
                ? presupuestoRepository.findById(dto.getPresupuestoId())
                    .orElseThrow(() -> new IllegalArgumentException("Presupuesto no encontrado"))
                : null;

        CuentaContable cuentaContable = dto.getCuentaContableId() != null
                ? cuentaContableRepository.findById(dto.getCuentaContableId())
                    .orElseThrow(() -> new IllegalArgumentException("Cuenta contable no encontrada"))
                : null;

        Moneda moneda = monedaRepository.findById(dto.getMonedaCodigo())
                .orElseThrow(() -> new IllegalArgumentException("Moneda no encontrada"));

        Factura factura = new Factura(
                dto.getNumero(),
                dto.getFecha(),
                proveedor,
                usuario,
                ordenCompra,
                presupuesto,
                cuentaContable,
                empresa,
                dto.getTotal(),
                moneda,
                TipoPago.valueOf(dto.getTipoPago()),
                dto.getObservaciones()
        );

        Factura saved = facturaRepository.save(factura);
        return toDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<FacturaDTO> listarFacturasPorEmpresa(Long empresaId) {
        return facturaRepository.findAllByEmpresa_Id(empresaId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private FacturaDTO toDTO(Factura factura) {
        FacturaDTO dto = new FacturaDTO();
        dto.setId(factura.getId());
        dto.setNumero(factura.getNumero());
        dto.setFecha(factura.getFecha());
        dto.setTotal(factura.getTotal());
        dto.setTipoPago(factura.getTipoPago().name());
        dto.setMonedaCodigo(factura.getMoneda() != null ? factura.getMoneda().getCodigo() : null);
        dto.setProveedorId(factura.getProveedor() != null ? factura.getProveedor().getId() : null);
        dto.setUsuarioId(factura.getUsuario() != null ? factura.getUsuario().getId() : null);
        dto.setOrdenCompraId(factura.getOrdenCompra() != null ? factura.getOrdenCompra().getId() : null);
        dto.setPresupuestoId(factura.getPresupuesto() != null ? factura.getPresupuesto().getId() : null);
        dto.setCuentaContableId(factura.getCuentaContable() != null ? factura.getCuentaContable().getId() : null);
        dto.setEmpresaId(factura.getEmpresa().getId());
        dto.setObservaciones(factura.getObservaciones());
        return dto;
    }
}
