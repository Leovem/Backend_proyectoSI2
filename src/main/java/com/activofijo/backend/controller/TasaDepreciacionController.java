package com.activofijo.backend.controller;


import com.activofijo.backend.dto.TasaDepreciacionCreateDTO;
import com.activofijo.backend.dto.TasaDepreciacionListDTO;
import com.activofijo.backend.models.TasaDepreciacion;
import com.activofijo.backend.services.TasaDepreciacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasa-depreciacion")
public class TasaDepreciacionController {

    @Autowired
    private TasaDepreciacionService service;

    @GetMapping
    public List<TasaDepreciacionListDTO> getAll() {
        return service.findAll();
    }

    @PostMapping
    public TasaDepreciacion create(@Valid @RequestBody TasaDepreciacionCreateDTO dto) {
        return service.save(dto);
    }
}
