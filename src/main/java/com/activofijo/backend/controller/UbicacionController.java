package com.activofijo.backend.controller;

import com.activofijo.backend.dto.UbicacionCreateDTO;
import com.activofijo.backend.dto.UbicacionDTO;
import com.activofijo.backend.services.UbicacionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ubicaciones")
public class UbicacionController {

    private final UbicacionService ubicacionService;
    public UbicacionController(UbicacionService ubicacionService) {
        this.ubicacionService = ubicacionService;
    }

    @PostMapping
    public UbicacionDTO crear(@Valid @RequestBody UbicacionCreateDTO dto) {
        return ubicacionService.crear(dto);
    }

    @GetMapping("/empresa/{empresaId}")
    public List<UbicacionDTO> listarPorEmpresa(@PathVariable Long empresaId) {
        return ubicacionService.listarPorEmpresa(empresaId);
    }

    @PutMapping("/{id}")
    public UbicacionDTO actualizar(@PathVariable Long id, @Valid @RequestBody UbicacionCreateDTO dto) {
        return ubicacionService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id, @RequestParam Long empresaId) {
        ubicacionService.eliminar(id, empresaId);
    }
}
