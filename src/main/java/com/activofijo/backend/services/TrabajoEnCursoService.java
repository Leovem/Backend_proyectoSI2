package com.activofijo.backend.services;


import com.activofijo.backend.models.TrabajoEnCurso;
import com.activofijo.backend.repository.TrabajoEnCursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrabajoEnCursoService {

    @Autowired
    private TrabajoEnCursoRepository repository;

    public List<TrabajoEnCurso> findAll() {
        return repository.findAll();
    }

    public Optional<TrabajoEnCurso> findById(Long id) {
        return repository.findById(id);
    }

    public TrabajoEnCurso save(TrabajoEnCurso t) {
        return repository.save(t);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
