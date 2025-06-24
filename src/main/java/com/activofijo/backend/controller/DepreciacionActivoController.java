package com.activofijo.backend.controller;


import com.activofijo.backend.dto.DepreciacionActivoCreateDTO;
import com.activofijo.backend.dto.DepreciacionActivoListDTO;
import com.activofijo.backend.models.DepreciacionActivo;
import com.activofijo.backend.services.DepreciacionActivoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/depreciacion-activo")
public class DepreciacionActivoController {

    @Autowired
    private DepreciacionActivoService service;

    @GetMapping
    public List<DepreciacionActivoListDTO> getAll() {
        return service.findAll();
    }

    @PostMapping
    public DepreciacionActivo create(@Valid @RequestBody DepreciacionActivoCreateDTO dto) {
        return service.save(dto);
    }
}

