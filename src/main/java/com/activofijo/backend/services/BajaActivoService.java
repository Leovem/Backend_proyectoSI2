package com.activofijo.backend.services;


import com.activofijo.backend.models.BajaActivo;
import com.activofijo.backend.repository.BajaActivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BajaActivoService {

    @Autowired
    private BajaActivoRepository repository;

    public List<BajaActivo> findAll() {
        return repository.findAll();
    }

    public Optional<BajaActivo> findById(Long id) {
        return repository.findById(id);
    }

    public BajaActivo save(BajaActivo b) {
        return repository.save(b);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
