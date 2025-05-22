package com.activofijo.backend.controller;

import com.activofijo.backend.dto.TipoDepreciacionCreateDTO;
import com.activofijo.backend.dto.TipoDepreciacionDTO;
import com.activofijo.backend.services.TipoDepreciacionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipo-depreciacion")
public class TipoDepreciacionController {

    private final TipoDepreciacionService tipoService;
    public TipoDepreciacionController(TipoDepreciacionService tipoService) {
        this.tipoService = tipoService;
    }

    @PostMapping
    public TipoDepreciacionDTO crear(@Valid @RequestBody TipoDepreciacionCreateDTO dto) {
        return tipoService.crear(dto);
    }

    @GetMapping
    public List<TipoDepreciacionDTO> listar() {
        return tipoService.listar();
    }

    @PutMapping("/{id}")
    public TipoDepreciacionDTO actualizar(@PathVariable Long id, @Valid @RequestBody TipoDepreciacionCreateDTO dto) {
        return tipoService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        tipoService.eliminar(id);
    }
}
