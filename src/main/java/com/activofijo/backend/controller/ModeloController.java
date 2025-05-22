package com.activofijo.backend.controller;

import com.activofijo.backend.dto.ModeloCreateDTO;
import com.activofijo.backend.dto.ModeloDTO;
import com.activofijo.backend.services.ModeloService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modelos")
public class ModeloController {

    private final ModeloService modeloService;
    public ModeloController(ModeloService modeloService) {
        this.modeloService = modeloService;
    }

    @PostMapping
    public ModeloDTO crear(@Valid @RequestBody ModeloCreateDTO dto) {
        return modeloService.crear(dto);
    }

    @GetMapping
    public List<ModeloDTO> listar() {
        return modeloService.listar();
    }

    @GetMapping("/marca/{marcaId}")
    public List<ModeloDTO> listarPorMarca(@PathVariable Long marcaId) {
        return modeloService.listarPorMarca(marcaId);
    }

    @PutMapping("/{id}")
    public ModeloDTO actualizar(@PathVariable Long id, @Valid @RequestBody ModeloCreateDTO dto) {
        return modeloService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        modeloService.eliminar(id);
    }
}
