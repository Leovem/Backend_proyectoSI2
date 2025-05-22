package com.activofijo.backend.controller;

import com.activofijo.backend.dto.ClasificacionActivoCreateDTO;
import com.activofijo.backend.dto.ClasificacionActivoDTO;
import com.activofijo.backend.services.ClasificacionActivoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clasificacion-activo")
public class ClasificacionActivoController {

    private final ClasificacionActivoService clasificacionActivoService;
    public ClasificacionActivoController(ClasificacionActivoService clasificacionActivoService) {
        this.clasificacionActivoService = clasificacionActivoService;
    }

    @PostMapping
    public ClasificacionActivoDTO crear(@Valid @RequestBody ClasificacionActivoCreateDTO dto) {
        return clasificacionActivoService.crear(dto);
    }

    @GetMapping
    public List<ClasificacionActivoDTO> listar() {
        return clasificacionActivoService.listar();
    }

    @PutMapping("/{id}")
    public ClasificacionActivoDTO actualizar(@PathVariable Long id, @Valid @RequestBody ClasificacionActivoCreateDTO dto) {
        return clasificacionActivoService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        clasificacionActivoService.eliminar(id);
    }
}
