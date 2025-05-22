package com.activofijo.backend.controller;

import com.activofijo.backend.dto.GrupoActivoCreateDTO;
import com.activofijo.backend.dto.GrupoActivoDTO;
import com.activofijo.backend.services.GrupoActivoService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupo-activo")

public class GrupoActivoController {

    private final GrupoActivoService grupoActivoService;
    public GrupoActivoController(GrupoActivoService grupoActivoService) {
        this.grupoActivoService = grupoActivoService;
    }

    @PostMapping
    public GrupoActivoDTO crear(@Valid @RequestBody GrupoActivoCreateDTO dto) {
        return grupoActivoService.crear(dto);
    }

    @GetMapping
    public List<GrupoActivoDTO> listar() {
        return grupoActivoService.listar();
    }

    @PutMapping("/{id}")
    public GrupoActivoDTO actualizar(@PathVariable Long id, @Valid @RequestBody GrupoActivoCreateDTO dto) {
        return grupoActivoService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        grupoActivoService.eliminar(id);
    }
}
