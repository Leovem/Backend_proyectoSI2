package com.activofijo.backend.controller;

import com.activofijo.backend.dto.TipoActivoCreateDTO;
import com.activofijo.backend.dto.TipoActivoDTO;
import com.activofijo.backend.services.TipoActivoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipo-activo")
public class TipoActivoController {

    private final TipoActivoService tipoActivoService;
    public TipoActivoController(TipoActivoService tipoActivoService) {
        this.tipoActivoService = tipoActivoService;
    }
    

    @PostMapping
    public TipoActivoDTO crear(@Valid @RequestBody TipoActivoCreateDTO dto) {
        return tipoActivoService.crear(dto);
    }

    @GetMapping
    public List<TipoActivoDTO> listar() {
        return tipoActivoService.listar();
    }

    @PutMapping("/{id}")
    public TipoActivoDTO actualizar(@PathVariable Long id, @Valid @RequestBody TipoActivoCreateDTO dto) {
        return tipoActivoService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        tipoActivoService.eliminar(id);
    }
}
