package com.activofijo.backend.services;

import com.activofijo.backend.dto.PagoSuscripcionDTO;
import com.activofijo.backend.models.PagoSuscripcion;
import com.activofijo.backend.models.Suscripcion;
import com.activofijo.backend.repository.PagoSuscripcionRepository;
import com.activofijo.backend.repository.SuscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagoSuscripcionService {

    @Autowired
    private PagoSuscripcionRepository pagoRepository;

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    public List<PagoSuscripcionDTO> obtenerTodos() {
        return pagoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public PagoSuscripcionDTO crear(PagoSuscripcionDTO dto) {
        PagoSuscripcion pago = toEntity(dto);
        return toDTO(pagoRepository.save(pago));
    }

    private PagoSuscripcionDTO toDTO(PagoSuscripcion p) {
        PagoSuscripcionDTO dto = new PagoSuscripcionDTO();
        dto.setId(p.getId());
        dto.setSuscripcionId(p.getSuscripcion().getId());
        dto.setFechaPago(p.getFechaPago());
        dto.setMonto(p.getMonto());
        dto.setMetodoPago(p.getMetodoPago());
        dto.setEstadoPago(p.getEstadoPago());
        dto.setObservaciones(p.getObservaciones());
        return dto;
    }

    private PagoSuscripcion toEntity(PagoSuscripcionDTO dto) {
        Suscripcion suscripcion = suscripcionRepository.findById(dto.getSuscripcionId()).orElseThrow();
        PagoSuscripcion p = new PagoSuscripcion();
        p.setId(dto.getId());
        p.setSuscripcion(suscripcion);
        p.setFechaPago(dto.getFechaPago());
        p.setMonto(dto.getMonto());
        p.setMetodoPago(dto.getMetodoPago());
        p.setEstadoPago(dto.getEstadoPago());
        p.setObservaciones(dto.getObservaciones());
        return p;
    }
}
