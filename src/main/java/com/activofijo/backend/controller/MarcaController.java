package com.activofijo.backend.controller;

import com.activofijo.backend.dto.MarcaCreateDTO;
import com.activofijo.backend.dto.MarcaDTO;
import com.activofijo.backend.services.MarcaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    private final MarcaService marcaService;
    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @PostMapping
    public MarcaDTO crear(@Valid @RequestBody MarcaCreateDTO dto) {
        return marcaService.crear(dto);
    }

    @GetMapping
    public List<MarcaDTO> listar() {
        return marcaService.listar();
    }

    @PutMapping("/{id}")
    public MarcaDTO actualizar(@PathVariable Long id, @Valid @RequestBody MarcaCreateDTO dto) {
        return marcaService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        marcaService.eliminar(id);
    }
}
