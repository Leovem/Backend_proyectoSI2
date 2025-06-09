package com.activofijo.backend.controller;

import com.activofijo.backend.dto.PlanDTO;
import com.activofijo.backend.services.PlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    @Autowired
    private PlanService planService;

    // GET /api/planes
    @GetMapping
    public ResponseEntity<List<PlanDTO>> obtenerTodos() {
        return ResponseEntity.ok(planService.obtenerTodos());
    }

    // GET /api/planes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PlanDTO> obtenerPorId(@PathVariable Long id) {
        PlanDTO dto = planService.obtenerPorId(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    // POST /api/planes
    @PostMapping
    public ResponseEntity<PlanDTO> crear(@Valid @RequestBody PlanDTO dto) {
        PlanDTO creado = planService.crear(dto);
        return ResponseEntity.ok(creado);
    }

    // DELETE /api/planes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        planService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
