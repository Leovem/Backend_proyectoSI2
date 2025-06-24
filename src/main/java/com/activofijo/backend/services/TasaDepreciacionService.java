package com.activofijo.backend.services;



import com.activofijo.backend.models.TasaDepreciacion;
import com.activofijo.backend.models.TipoActivo;
import com.activofijo.backend.dto.TasaDepreciacionCreateDTO;
import com.activofijo.backend.dto.TasaDepreciacionListDTO;
import com.activofijo.backend.repository.TasaDepreciacionRepository;
import com.activofijo.backend.repository.TipoActivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TasaDepreciacionService {

    @Autowired
    private TasaDepreciacionRepository repository;

    @Autowired
    private TipoActivoRepository tipoActivoRepository;

    public List<TasaDepreciacionListDTO> findAll() {
        return repository.findAll().stream().map(td -> {
            TasaDepreciacionListDTO dto = new TasaDepreciacionListDTO();
            dto.setId(td.getId());
            dto.setTipoActivoNombre(td.getTipoActivo().getNombre());
            dto.setPorcentaje(td.getPorcentaje());
            dto.setVidaUtil(td.getVidaUtil());
            dto.setFechaVigencia(td.getFechaVigencia());
            return dto;
        }).collect(Collectors.toList());
    }

    public TasaDepreciacion save(TasaDepreciacionCreateDTO dto) {
        TasaDepreciacion td = new TasaDepreciacion();
        TipoActivo tipo = tipoActivoRepository.findById(dto.getTipoActivoId()).orElseThrow();
        td.setTipoActivo(tipo);
        td.setPorcentaje(dto.getPorcentaje());
        td.setVidaUtil(dto.getVidaUtil());
        td.setFechaVigencia(dto.getFechaVigencia());
        return repository.save(td);
    }
}


