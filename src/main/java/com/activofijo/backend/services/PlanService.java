package com.activofijo.backend.services;

import com.activofijo.backend.dto.PlanDTO;
import com.activofijo.backend.models.Plan;
import com.activofijo.backend.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    public List<PlanDTO> obtenerTodos() {
        return planRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public PlanDTO crear(PlanDTO dto) {
        Plan plan = toEntity(dto);
        return toDTO(planRepository.save(plan));
    }

    public PlanDTO obtenerPorId(Long id) {
        return planRepository.findById(id).map(this::toDTO).orElse(null);
    }

    public void eliminar(Long id) {
        planRepository.deleteById(id);
    }

    private PlanDTO toDTO(Plan plan) {
        PlanDTO dto = new PlanDTO();
        dto.setId(plan.getId());
        dto.setNombre(plan.getNombre());
        dto.setDescripcion(plan.getDescripcion());
        dto.setPrecioMensual(plan.getPrecioMensual());
        dto.setPrecioSemestral(plan.getPrecioSemestral());
        dto.setPrecioAnual(plan.getPrecioAnual());
        dto.setLimiteUsuarios(plan.getLimiteUsuarios());
        dto.setLimiteProyectos(plan.getLimiteProyectos());
        dto.setLimiteActivos(plan.getLimiteActivos());
        dto.setActivo(plan.isActivo());
        return dto;
    }

    private Plan toEntity(PlanDTO dto) {
        Plan plan = new Plan();
        plan.setId(dto.getId());
        plan.setNombre(dto.getNombre());
        plan.setDescripcion(dto.getDescripcion());
        plan.setPrecioMensual(dto.getPrecioMensual());
        plan.setPrecioSemestral(dto.getPrecioSemestral());
        plan.setPrecioAnual(dto.getPrecioAnual());
        plan.setLimiteUsuarios(dto.getLimiteUsuarios());
        plan.setLimiteProyectos(dto.getLimiteProyectos());
        plan.setLimiteActivos(dto.getLimiteActivos());
        plan.setActivo(dto.isActivo());
        return plan;
    }
}
