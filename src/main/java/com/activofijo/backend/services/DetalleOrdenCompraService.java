package com.activofijo.backend.services;

import com.activofijo.backend.dto.DetalleOrdenCompraCreateDTO;
import com.activofijo.backend.dto.DetalleOrdenCompraDTO;
import com.activofijo.backend.models.DetalleOrdenCompra;
import com.activofijo.backend.models.OrdenCompra;
import com.activofijo.backend.repository.DetalleOrdenCompraRepository;
import com.activofijo.backend.repository.OrdenCompraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetalleOrdenCompraService {

    private final DetalleOrdenCompraRepository detalleRepository;
    private final OrdenCompraRepository ordenRepository;

    public DetalleOrdenCompraService(DetalleOrdenCompraRepository detalleRepository,
                                     OrdenCompraRepository ordenRepository) {
        this.detalleRepository = detalleRepository;
        this.ordenRepository = ordenRepository;
    }

    @Transactional
    public DetalleOrdenCompraDTO crearDetalle(DetalleOrdenCompraCreateDTO dto) {
        OrdenCompra orden = ordenRepository.findById(dto.getOrdenId())
                .orElseThrow(() -> new IllegalArgumentException("Orden de compra no encontrada con ID: " + dto.getOrdenId()));

        DetalleOrdenCompra detalle = new DetalleOrdenCompra();
        detalle.setOrdenCompra(orden);
        detalle.setDescripcion(dto.getDescripcion());
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioEstimado(dto.getPrecioEstimado());

        DetalleOrdenCompra saved = detalleRepository.save(detalle);
        return toDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<DetalleOrdenCompraDTO> listarPorOrden(Long ordenId) {
        return detalleRepository.findAllByOrdenCompra_Id(ordenId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public DetalleOrdenCompraDTO actualizarDetalle(Long id, DetalleOrdenCompraCreateDTO dto) {
        DetalleOrdenCompra detalle = detalleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Detalle no encontrado con ID: " + id));

        if (!detalle.getOrdenCompra().getId().equals(dto.getOrdenId())) {
            throw new IllegalArgumentException("El detalle no pertenece a la orden especificada.");
        }

        detalle.setDescripcion(dto.getDescripcion());
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioEstimado(dto.getPrecioEstimado());

        DetalleOrdenCompra actualizado = detalleRepository.save(detalle);
        return toDTO(actualizado);
    }

    @Transactional
    public void eliminarDetalle(Long id) {
        DetalleOrdenCompra detalle = detalleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Detalle no encontrado con ID: " + id));
        detalleRepository.delete(detalle);
    }

    private DetalleOrdenCompraDTO toDTO(DetalleOrdenCompra detalle) {
        DetalleOrdenCompraDTO dto = new DetalleOrdenCompraDTO();
        dto.setId(detalle.getId());
        dto.setOrdenId(detalle.getOrdenCompra().getId());
        dto.setDescripcion(detalle.getDescripcion());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioEstimado(detalle.getPrecioEstimado());
        dto.setSubtotal(detalle.getSubtotal());
        return dto;
    }
}
