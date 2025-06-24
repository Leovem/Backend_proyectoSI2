package com.activofijo.backend.controller;


import com.activofijo.backend.dto.RevaluacionActivoCreateDTO;
import com.activofijo.backend.dto.RevaluacionActivoListDTO;
import com.activofijo.backend.models.RevaluacionActivo;
import com.activofijo.backend.services.RevaluacionActivoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/revaluacion-activo")
public class RevaluacionActivoController {

    @Autowired
    private RevaluacionActivoService service;

    @GetMapping
    public List<RevaluacionActivoListDTO> getAll() {
        return service.findAll();
    }

    @PostMapping
    public RevaluacionActivo create(@Valid @RequestBody RevaluacionActivoCreateDTO dto) {
        return service.save(dto);
    }
}
