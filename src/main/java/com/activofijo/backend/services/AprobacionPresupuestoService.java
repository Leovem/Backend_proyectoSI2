package com.activofijo.backend.services;


import com.activofijo.backend.models.AprobacionPresupuesto;
import com.activofijo.backend.repository.AprobacionPresupuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AprobacionPresupuestoService {

    @Autowired
    private AprobacionPresupuestoRepository repository;

    public List<AprobacionPresupuesto> findAll() {
        return repository.findAll();
    }

    public Optional<AprobacionPresupuesto> findById(Long id) {
        return repository.findById(id);
    }

    public AprobacionPresupuesto save(AprobacionPresupuesto ap) {
        return repository.save(ap);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
