package com.activofijo.backend.controller;

import com.activofijo.backend.dto.TipoContratoCreateDTO;
import com.activofijo.backend.dto.TipoContratoDTO;
import com.activofijo.backend.services.TipoContratoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipo-contrato")

public class TipoContratoController {

    private final TipoContratoService tipoContratoService;
    public TipoContratoController(TipoContratoService tipoContratoService) {
        this.tipoContratoService = tipoContratoService;
    }

    @PostMapping
    public TipoContratoDTO crear(@Valid @RequestBody TipoContratoCreateDTO dto) {
        return tipoContratoService.crear(dto);
    }

    @GetMapping
    public List<TipoContratoDTO> listar() {
        return tipoContratoService.listar();
    }

    @PutMapping("/{id}")
    public TipoContratoDTO actualizar(@PathVariable Long id, @Valid @RequestBody TipoContratoCreateDTO dto) {
        return tipoContratoService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        tipoContratoService.eliminar(id);
    }
}
