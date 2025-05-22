package com.activofijo.backend.controller;

import com.activofijo.backend.dto.MetodoDepreciacionCreateDTO;
import com.activofijo.backend.dto.MetodoDepreciacionDTO;
import com.activofijo.backend.services.MetodoDepreciacionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metodo-depreciacion")
public class MetodoDepreciacionController {

    private final MetodoDepreciacionService metodoService;
    public MetodoDepreciacionController(MetodoDepreciacionService metodoService) {
        this.metodoService = metodoService;
    }

    @PostMapping
    public MetodoDepreciacionDTO crear(@Valid @RequestBody MetodoDepreciacionCreateDTO dto) {
        return metodoService.crear(dto);
    }

    @GetMapping
    public List<MetodoDepreciacionDTO> listar() {
        return metodoService.listar();
    }

    @PutMapping("/{id}")
    public MetodoDepreciacionDTO actualizar(@PathVariable Long id, @Valid @RequestBody MetodoDepreciacionCreateDTO dto) {
        return metodoService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        metodoService.eliminar(id);
    }
}
