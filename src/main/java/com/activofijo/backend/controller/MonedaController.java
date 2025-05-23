package com.activofijo.backend.controller;

import com.activofijo.backend.dto.MonedaCreateDTO;
import com.activofijo.backend.dto.MonedaDTO;
import com.activofijo.backend.dto.MonedaUpdateDTO;
import com.activofijo.backend.models.Moneda;
import com.activofijo.backend.repository.MonedaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/monedas")
public class MonedaController {

    @Autowired
    private MonedaRepository monedaRepository;

    // Listar todas las monedas
    @GetMapping
    public List<MonedaDTO> listarMonedas() {
        return monedaRepository.findAll().stream()
                .map(m -> new MonedaDTO(m.getCodigo(), m.getNombre(), m.getActivo()))
                .toList();
    }

    // Obtener una moneda por su código (ej: USD, BOB)
    @GetMapping("/{codigo}")
    public ResponseEntity<MonedaDTO> obtenerMoneda(@PathVariable String codigo) {
        return monedaRepository.findById(codigo.toUpperCase())
                .map(m -> ResponseEntity.ok(new MonedaDTO(m.getCodigo(), m.getNombre(), m.getActivo())))
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva moneda
    @PostMapping
    public ResponseEntity<MonedaDTO> crearMoneda(@Valid @RequestBody MonedaCreateDTO dto) {
        if (monedaRepository.existsById(dto.getCodigo())) {
            return ResponseEntity.badRequest().build();
        }
        Moneda moneda = new Moneda(dto.getCodigo(), dto.getNombre(), true);
        monedaRepository.save(moneda);
        return ResponseEntity.ok(new MonedaDTO(moneda.getCodigo(), moneda.getNombre(), moneda.getActivo()));
    }

    // Actualizar una moneda existente
    @PutMapping("/{codigo}")
    public ResponseEntity<MonedaDTO> actualizarMoneda(
            @PathVariable String codigo,
            @Valid @RequestBody MonedaUpdateDTO detalles) {

        Optional<Moneda> monedaOptional = monedaRepository.findById(codigo.toUpperCase());
        if (monedaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Moneda moneda = monedaOptional.get();
        moneda.setNombre(detalles.getNombre());
        moneda = monedaRepository.save(moneda);

        return ResponseEntity.ok(new MonedaDTO(moneda.getCodigo(), moneda.getNombre(), moneda.getActivo()));
    }

    // Eliminar una moneda
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminarMoneda(@PathVariable String codigo) {
        if (!monedaRepository.existsById(codigo.toUpperCase())) {
            return ResponseEntity.notFound().build();
        }
        monedaRepository.deleteById(codigo.toUpperCase());
        return ResponseEntity.noContent().build();
    }
}
