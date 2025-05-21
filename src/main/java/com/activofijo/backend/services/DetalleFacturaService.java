package com.activofijo.backend.services;

import com.activofijo.backend.dto.DetalleFacturaCreateDTO;
import com.activofijo.backend.dto.DetalleFacturaDTO;
import com.activofijo.backend.models.DetalleFactura;
import com.activofijo.backend.models.Factura;
import com.activofijo.backend.repository.DetalleFacturaRepository;
import com.activofijo.backend.repository.FacturaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetalleFacturaService {

    private final DetalleFacturaRepository detalleRepository;
    private final FacturaRepository facturaRepository;

    public DetalleFacturaService(DetalleFacturaRepository detalleRepository, FacturaRepository facturaRepository) {
        this.detalleRepository = detalleRepository;
        this.facturaRepository = facturaRepository;
    }

    @Transactional
    public DetalleFacturaDTO crear(DetalleFacturaCreateDTO dto) {
        Factura factura = facturaRepository.findById(dto.getFacturaId())
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada con ID: " + dto.getFacturaId()));

        DetalleFactura detalle = new DetalleFactura();
        detalle.setFactura(factura);
        detalle.setDescripcion(dto.getDescripcion());
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());

        DetalleFactura guardado = detalleRepository.save(detalle);
        return toDTO(guardado);
    }

    @Transactional(readOnly = true)
    public List<DetalleFacturaDTO> listarPorFactura(Long facturaId) {
        return detalleRepository.findAllByFactura_Id(facturaId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public DetalleFacturaDTO actualizar(Long id, DetalleFacturaCreateDTO dto) {
        DetalleFactura detalle = detalleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Detalle no encontrado con ID: " + id));

        if (!detalle.getFactura().getId().equals(dto.getFacturaId())) {
            throw new IllegalArgumentException("El detalle no pertenece a la factura especificada.");
        }

        detalle.setDescripcion(dto.getDescripcion());
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());

        DetalleFactura actualizado = detalleRepository.save(detalle);
        return toDTO(actualizado);
    }

    @Transactional
    public void eliminar(Long id) {
        DetalleFactura detalle = detalleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Detalle no encontrado con ID: " + id));
        detalleRepository.delete(detalle);
    }

    private DetalleFacturaDTO toDTO(DetalleFactura detalle) {
        DetalleFacturaDTO dto = new DetalleFacturaDTO();
        dto.setId(detalle.getId());
        dto.setFacturaId(detalle.getFactura().getId());
        dto.setDescripcion(detalle.getDescripcion());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubtotal(detalle.getSubtotal());
        return dto;
    }
}
