package com.activofijo.backend.services;

import com.activofijo.backend.dto.RegistroEmpresaRequest;
import com.activofijo.backend.models.Empresa;
import com.activofijo.backend.models.Plan;
import com.activofijo.backend.models.Suscripcion;
import com.activofijo.backend.repository.PlanRepository;
import com.activofijo.backend.repository.SuscripcionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RegistroEmpresaService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @Transactional
    public void registrarEmpresaConSuscripcion(RegistroEmpresaRequest request) {
        // Llamar función SQL
        entityManager.createNativeQuery("SELECT inicializar_nueva_empresa(:nombre, :rfc, :usuario, :nombreCompleto, :email, :contrasena)")
                .setParameter("nombre", request.getNombreEmpresa())
                .setParameter("rfc", request.getRfc())
                .setParameter("usuario", request.getUsuario())
                .setParameter("nombreCompleto", request.getNombreCompleto())
                .setParameter("email", request.getEmail())
                .setParameter("contrasena", request.getContrasena())
                .executeUpdate();

        // Buscar empresa creada (asumiendo RFC único)
        Empresa empresa = (Empresa) entityManager
                .createQuery("SELECT e FROM Empresa e WHERE e.rfc = :rfc")
                .setParameter("rfc", request.getRfc())
                .getSingleResult();

        // Calcular fechas de suscripción
        LocalDate inicio = LocalDate.now();
        LocalDate fin = switch (request.getTipoPeriodo()) {
            case "Mensual" -> inicio.plusMonths(1);
            case "Semestral" -> inicio.plusMonths(6);
            case "Anual" -> inicio.plusYears(1);
            default -> throw new IllegalArgumentException("Periodo inválido");
        };

        Plan plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new IllegalArgumentException("Plan no encontrado"));

        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setEmpresa(empresa);
        suscripcion.setPlan(plan);
        suscripcion.setFechaInicio(inicio);
        suscripcion.setFechaFin(fin);
        suscripcion.setTipoPeriodo(request.getTipoPeriodo());
        suscripcion.setEstado("Activa");

        suscripcionRepository.save(suscripcion);
    }
}
