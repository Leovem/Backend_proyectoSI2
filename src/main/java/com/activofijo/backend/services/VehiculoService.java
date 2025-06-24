package com.activofijo.backend.services;


import com.activofijo.backend.models.Vehiculo;
import com.activofijo.backend.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository repository;

    public List<Vehiculo> findAll() {
        return repository.findAll();
    }

    public Optional<Vehiculo> findById(Long id) {
        return repository.findById(id);
    }

    public Vehiculo save(Vehiculo v) {
        return repository.save(v);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
