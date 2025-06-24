package com.activofijo.backend.services;



import com.activofijo.backend.dto.DepreciacionActivoCreateDTO;
import com.activofijo.backend.dto.DepreciacionActivoListDTO;
//import com.activofijo.backend.models.Activo;
//import com.activofijo.backend.models.MetodoDepreciacion;
import com.activofijo.backend.models.DepreciacionActivo;
import com.activofijo.backend.repository.ActivoRepository;
import com.activofijo.backend.repository.MetodoDepreciacionRepository;
import com.activofijo.backend.repository.DepreciacionActivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepreciacionActivoService {

    @Autowired
    private DepreciacionActivoRepository repository;

    @Autowired
    private ActivoRepository activoRepository;

    @Autowired
    private MetodoDepreciacionRepository metodoRepository;

    public List<DepreciacionActivoListDTO> findAll() {
        return repository.findAll().stream().map(da -> {
            DepreciacionActivoListDTO dto = new DepreciacionActivoListDTO();
            dto.setId(da.getId());
            dto.setActivoNombre(da.getActivo().getNombre()); // Asume que Activo tiene getNombre()
            dto.setMetodoNombre(da.getMetodoDepreciacion().getNombre()); // Asume que Método también
            dto.setFecha(da.getFecha());
            dto.setValor(da.getValor());
            dto.setMoneda(da.getMoneda());
            return dto;
        }).collect(Collectors.toList());
    }

    public DepreciacionActivo save(DepreciacionActivoCreateDTO dto) {
        DepreciacionActivo da = new DepreciacionActivo();
        da.setActivo(activoRepository.findById(dto.getActivoId()).orElseThrow());
        da.setMetodoDepreciacion(metodoRepository.findById(dto.getMetodoDepreciacionId()).orElseThrow());
        da.setFecha(dto.getFecha());
        da.setValor(dto.getValor());
        da.setMoneda(dto.getMoneda());

        return repository.save(da);
    }
}
