package com.activofijo.backend.services;

import com.activofijo.backend.models.*;
import com.activofijo.backend.dto.*;
import com.activofijo.backend.exception.BadRequestException;
//import com.activofijo.backend.exception.UnauthorizedException;
import com.activofijo.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Service
public class SubscriptionService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private RolRepository rolRepo;

    @Autowired
    private EmpresaRepository empresaRepo;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @Autowired
    private PagoSuscripcionRepository pagoSuscripcionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public Long crearEmpresa(EmpresaCreateDTO request) {
        String sql = "SELECT inicializar_nueva_empresa(?, ?)";
        return jdbcTemplate.queryForObject(sql, Long.class, request.getNombre(), request.getRfc());
    }

    @Transactional
    public SuscripcionDTO crearSuscripcion(SuscripcionCreateDTO request) {
        Empresa empresa = empresaRepository.findById(request.getEmpresaId())
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada"));
        Plan plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new IllegalArgumentException("Plan no encontrado"));

        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setEmpresa(empresa);
        suscripcion.setPlan(plan);
        suscripcion.setTipoPeriodo(request.getTipoPeriodo());
        suscripcion.setEstado("Activa");

        LocalDate fechaInicio = LocalDate.now();
        suscripcion.setFechaInicio(fechaInicio);
        switch (request.getTipoPeriodo()) {
            case "Mensual":
                suscripcion.setFechaFin(fechaInicio.plusMonths(1));
                break;
            case "Semestral":
                suscripcion.setFechaFin(fechaInicio.plusMonths(6));
                break;
            case "Anual":
                suscripcion.setFechaFin(fechaInicio.plusYears(1));
                break;
            default:
                throw new IllegalArgumentException("Tipo de periodo inválido");
        }

        suscripcion = suscripcionRepository.save(suscripcion);
        return toDTO(suscripcion);
    }

    @Transactional
    public PagoSuscripcionDTO registrarPago(PagoSuscripcionCreateDTO request) {
        Suscripcion suscripcion = suscripcionRepository.findById(request.getSuscripcionId())
                .orElseThrow(() -> new IllegalArgumentException("Suscripción no encontrada"));

        PagoSuscripcion pago = new PagoSuscripcion();
        pago.setSuscripcion(suscripcion);
        pago.setMonto(request.getMonto());
        pago.setMetodoPago(request.getMetodoPago());
        pago.setEstadoPago("Completado");
        pago.setObservaciones(request.getObservaciones());

        pago = pagoSuscripcionRepository.save(pago);
        return toDTO(pago);
    }

    @Transactional
    public UsuarioDTO crearUsuarioAdministrador(UsuarioCreateDTO dto) {
        if (usuarioRepository.findByUsuarioAndEmpresaId(dto.getUsuario(), dto.getEmpresaId()).isPresent()) {
            throw new BadRequestException("Ya existe un usuario con ese nombre en la empresa");
        }
        if (usuarioRepository.findByEmailAndEmpresaId(dto.getEmail(), dto.getEmpresaId()).isPresent()) {
            throw new BadRequestException("Ya existe un usuario con ese email en la empresa");
        }

        Rol rol = rolRepo.findById(dto.getRolId())
                .orElseThrow(() -> new BadRequestException("Rol no encontrado"));
        Empresa empresa = empresaRepo.findById(dto.getEmpresaId())
                .orElseThrow(() -> new BadRequestException("Empresa no encontrada"));

        Usuario u = new Usuario();
        u.setUsuario(dto.getUsuario());
        u.setNombreCompleto(dto.getNombreCompleto());
        u.setEmail(dto.getEmail());
        u.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        u.setRol(rol);
        u.setEmpresa(empresa);
        u.setActivo(true);

        u = usuarioRepository.save(u);
        return toDTO(u);
    }

    @Transactional
    public UsuarioDTO adquirirPlan(AdquisicionPlanRequestDTO request) {
        // 1. Crear la empresa
        Long empresaId = crearEmpresa(request.getEmpresa());
        System.out.println("✅ Empresa creada con ID: " + empresaId);

        // 2. Crear suscripción
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new IllegalStateException("Empresa recién creada no encontrada"));
        Plan plan = planRepository.findById(request.getSuscripcion().getPlanId())
                .orElseThrow(() -> new IllegalArgumentException("Plan no encontrado"));

        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setEmpresa(empresa);
        suscripcion.setPlan(plan);
        suscripcion.setTipoPeriodo(request.getSuscripcion().getTipoPeriodo());
        suscripcion.setEstado("Activa");

        LocalDate fechaInicio = LocalDate.now();
        suscripcion.setFechaInicio(fechaInicio);
        switch (request.getSuscripcion().getTipoPeriodo()) {
            case "Mensual" -> suscripcion.setFechaFin(fechaInicio.plusMonths(1));
            case "Semestral" -> suscripcion.setFechaFin(fechaInicio.plusMonths(6));
            case "Anual" -> suscripcion.setFechaFin(fechaInicio.plusYears(1));
            default -> throw new IllegalArgumentException("Tipo de periodo inválido");
        }
        suscripcion = suscripcionRepository.save(suscripcion);

        // 3. Registrar el pago
        PagoSuscripcion pago = new PagoSuscripcion();
        pago.setSuscripcion(suscripcion);
        pago.setMonto(request.getPago().getMonto());
        pago.setMetodoPago(request.getPago().getMetodoPago());
        pago.setEstadoPago(request.getPago().getEstadoPago());
        pago.setObservaciones(request.getPago().getObservaciones());
        pago = pagoSuscripcionRepository.save(pago);

        // 4. Crear usuario administrador
        UsuarioCreateDTO usuarioDTO = request.getUsuarioAdmin();
        if (usuarioRepository.findByUsuarioAndEmpresaId(usuarioDTO.getUsuario(), empresaId).isPresent()) {
            throw new BadRequestException("Ya existe un usuario con ese nombre en la empresa");
        }
        if (usuarioRepository.findByEmailAndEmpresaId(usuarioDTO.getEmail(), empresaId).isPresent()) {
            throw new BadRequestException("Ya existe un usuario con ese email en la empresa");
        }

        Rol rol = rolRepo.findByNombreAndEmpresaId("Administrador", empresaId)
                .orElseThrow(() -> new BadRequestException("Rol Administrador no encontrado"));

        Usuario usuario = new Usuario();
        usuario.setUsuario(usuarioDTO.getUsuario());
        usuario.setNombreCompleto(usuarioDTO.getNombreCompleto());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena()));
        usuario.setRol(rol);
        usuario.setEmpresa(empresa);
        usuario.setActivo(true);
        usuario = usuarioRepository.save(usuario);

        return toDTO(usuario);
    }

    private UsuarioDTO toDTO(Usuario u) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(u.getId());
        dto.setUsuario(u.getUsuario());
        dto.setNombreCompleto(u.getNombreCompleto());
        dto.setEmail(u.getEmail());
        dto.setRolId(u.getRol().getId());
        dto.setRolNombre(u.getRol().getNombre());
        dto.setEmpresaId(u.getEmpresa().getId());
        dto.setFechaUltimoAcceso(u.getFechaUltimoAcceso());
        dto.setActivo(u.getActivo());
        return dto;
    }

    private SuscripcionDTO toDTO(Suscripcion s) {
        SuscripcionDTO dto = new SuscripcionDTO();
        dto.setId(s.getId());
        dto.setEmpresaId(s.getEmpresa().getId());
        dto.setPlanId(s.getPlan().getId());
        dto.setFechaInicio(s.getFechaInicio());
        dto.setFechaFin(s.getFechaFin());
        dto.setTipoPeriodo(s.getTipoPeriodo());
        dto.setEstado(s.getEstado());
        return dto;
    }

    private PagoSuscripcionDTO toDTO(PagoSuscripcion p) {
        PagoSuscripcionDTO dto = new PagoSuscripcionDTO();
        dto.setId(p.getId());
        dto.setSuscripcionId(p.getSuscripcion().getId());
        dto.setFechaPago(p.getFechaPago());
        dto.setMonto(p.getMonto());
        dto.setMetodoPago(p.getMetodoPago());
        dto.setEstadoPago(p.getEstadoPago());
        dto.setObservaciones(p.getObservaciones());
        return dto;
    }
}