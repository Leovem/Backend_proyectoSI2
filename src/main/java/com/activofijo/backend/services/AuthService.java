package com.activofijo.backend.services;

import com.activofijo.backend.dto.LoginRequest;
import com.activofijo.backend.dto.UsuarioCreateDTO;
import com.activofijo.backend.dto.UsuarioDTO;
import com.activofijo.backend.exception.BadRequestException;
import com.activofijo.backend.exception.UnauthorizedException;
import com.activofijo.backend.models.Empresa;
import com.activofijo.backend.models.Rol;
import com.activofijo.backend.models.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.activofijo.backend.repository.EmpresaRepository;
import com.activofijo.backend.repository.RolRepository;
import com.activofijo.backend.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.OffsetDateTime;
//import java.time.ZoneId;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final EmpresaRepository empresaRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuditoriaService auditoriaService;

    public AuthService(UsuarioRepository usuarioRepo,
            RolRepository rolRepo,
            EmpresaRepository empresaRepo,
            PasswordEncoder passwordEncoder,
            AuditoriaService auditoriaService) {
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
        this.empresaRepo = empresaRepo;
        this.passwordEncoder = passwordEncoder;
        this.auditoriaService = auditoriaService;
    }

    @Transactional
    public UsuarioDTO login(LoginRequest req, String ipCliente) {
        logger.info("[AuthService] Login recibido para usuario: {}", req.getUsuario());
        Usuario usuario = usuarioRepo.findByUsuario(req.getUsuario()).orElse(null);
        

        if (usuario == null) {
            try {
                auditoriaService.registrarLoginFallido(req.getUsuario(), ipCliente, "Usuario no encontrado");
            } catch (Exception e) {
                logger.warn("Error auditando login fallido (usuario no encontrado): {}", e.getMessage());
            }
            throw new UnauthorizedException("Usuario o contraseña inválidos");
        }
        // 2) Verificar contraseña
        boolean match = passwordEncoder.matches(req.getContrasena(), usuario.getContrasena());

        if (!match) {
            try {
                auditoriaService.registrarLoginFallido(req.getUsuario(), ipCliente, "Contraseña incorrecta");
            } catch (Exception e) {
                logger.warn("Error auditando login fallido (contraseña incorrecta): {}", e.getMessage());
            }
            throw new UnauthorizedException("Usuario o contraseña inválidos");
        }

        usuario.setFechaUltimoAcceso(OffsetDateTime.now());
        usuarioRepo.save(usuario);

        try {
            auditoriaService.registrarLoginExitoso(usuario.getId(), usuario.getEmpresa().getId(), ipCliente);
        } catch (Exception e) {
            logger.error("Error registrando auditoría de login exitoso", e);
        }
        // 4) Convertir a DTO que incluye empresaId
        UsuarioDTO dto = toDTO(usuario);
        logger.info("[AuthService] login() SUCCESS, DTO = {}", dto);
        return dto;
    }

    @Transactional
    public UsuarioDTO register(UsuarioCreateDTO dto, String ipCliente, String username) {
        if (usuarioRepo.findByUsuarioAndEmpresaId(dto.getUsuario(), dto.getEmpresaId()).isPresent()) {
            throw new BadRequestException("Ya existe un usuario con ese nombre en la empresa");
        }
        if (usuarioRepo.findByEmailAndEmpresaId(dto.getEmail(), dto.getEmpresaId()).isPresent()) {
            throw new BadRequestException("Ya existe un usuario con ese email en la empresa");
        }

        Usuario usuarioCreador = usuarioRepo.findByUsuarioAndEmpresaId(username, dto.getEmpresaId())
                .orElseThrow(() -> new BadRequestException("Usuario creador no encontrado"));

        Long idCreador = usuarioCreador.getId();

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

        u = usuarioRepo.save(u);

        try {
            auditoriaService.registrarCreacionUsuario(u, idCreador, ipCliente);
        } catch (Exception e) {
            logger.warn("No se pudo registrar auditoría de creación de usuario: {}", e.getMessage());
        }

        return toDTO(u);
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
}
