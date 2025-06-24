package com.activofijo.backend.services;


import com.activofijo.backend.dto.RevaluacionActivoCreateDTO;
import com.activofijo.backend.dto.RevaluacionActivoListDTO;
import com.activofijo.backend.models.Activo;
import com.activofijo.backend.models.RevaluacionActivo;
import com.activofijo.backend.repository.ActivoRepository;
import com.activofijo.backend.repository.RevaluacionActivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RevaluacionActivoService {

    @Autowired
    private RevaluacionActivoRepository repository;

    @Autowired
    private ActivoRepository activoRepository;

    public List<RevaluacionActivoListDTO> findAll() {
        return repository.findAll().stream().map(r -> {
            RevaluacionActivoListDTO dto = new RevaluacionActivoListDTO();
            dto.setId(r.getId());
            dto.setActivoNombre(r.getActivo().getNombre()); // Asegúrate que Activo tiene getNombre()
            dto.setFecha(r.getFecha());
            dto.setNuevoValor(r.getNuevoValor());
            dto.setMotivo(r.getMotivo());
            return dto;
        }).collect(Collectors.toList());
    }

    public RevaluacionActivo save(RevaluacionActivoCreateDTO dto) {
        Activo activo = activoRepository.findById(dto.getActivoId()).orElseThrow();
        RevaluacionActivo r = new RevaluacionActivo();
        r.setActivo(activo);
        r.setFecha(dto.getFecha());
        r.setNuevoValor(dto.getNuevoValor());
        r.setMotivo(dto.getMotivo());
        return repository.save(r);
    }
}
