package com.activofijo.backend.controller;

import com.activofijo.backend.dto.PrivilegioDTO;
import com.activofijo.backend.repository.PrivilegioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/privilegios")
public class PrivilegioController {

    private final PrivilegioRepository privilegioRepository;

    public PrivilegioController(PrivilegioRepository privilegioRepository) {
        this.privilegioRepository = privilegioRepository;
    }

    @GetMapping
    public ResponseEntity<List<PrivilegioDTO>> listarTodos() {
        List<PrivilegioDTO> lista = privilegioRepository.findAll()
            .stream()
            .map(p -> new PrivilegioDTO(p.getId(), p.getNombre()))
            .toList();
        return ResponseEntity.ok(lista);
    }
}
