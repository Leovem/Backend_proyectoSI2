package com.activofijo.backend.services;

import com.activofijo.backend.dto.UsuarioCreateDTO;
import com.activofijo.backend.dto.UsuarioDTO;
import com.activofijo.backend.exception.BadRequestException;
import com.activofijo.backend.exception.NotFoundException;
//import com.activofijo.backend.models.Empresa;
import com.activofijo.backend.models.Rol;
import com.activofijo.backend.models.Usuario;
import com.activofijo.backend.repository.EmpresaRepository;
import com.activofijo.backend.repository.RolRepository;
import com.activofijo.backend.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final EmpresaRepository empresaRepo;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepo,
                          RolRepository rolRepo,
                          EmpresaRepository empresaRepo,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
        this.empresaRepo = empresaRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Lista todos los usuarios de una empresa.
     */
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listAll(Long empresaId) {
        empresaRepo.findById(empresaId)
                   .orElseThrow(() -> new NotFoundException("Empresa no encontrada: " + empresaId));
        return usuarioRepo.findByEmpresaId(empresaId)
                          .stream()
                          .map(this::toDTO)
                          .collect(Collectors.toList());
    }

    /**
     * Lista todos los usuarios activos de una empresa.
     */
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listActive(Long empresaId) {
        empresaRepo.findById(empresaId)
                   .orElseThrow(() -> new NotFoundException("Empresa no encontrada: " + empresaId));
        return usuarioRepo.findByEmpresaIdAndActivoTrue(empresaId)
                          .stream()
                          .map(this::toDTO)
                          .collect(Collectors.toList());
    }

    /**
     * Busca un usuario por su nombre de usuario y empresa.
     */
    @Transactional(readOnly = true)
    public UsuarioDTO findByUsuario(String usuario, Long empresaId) {
        return usuarioRepo.findByUsuarioAndEmpresaId(usuario, empresaId)
                          .map(this::toDTO)
                          .orElseThrow(() -> new NotFoundException(
                              "Usuario '" + usuario + "' no encontrado en empresa " + empresaId));
    }

    /**
     * Busca un usuario por su ID.
     */
    @Transactional(readOnly = true)
    public UsuarioDTO findById(Long id) {
        return usuarioRepo.findById(id)
                          .map(this::toDTO)
                          .orElseThrow(() -> new NotFoundException(
                              "Usuario con id " + id + " no encontrado"));
    }

    /**
     * Actualiza un usuario existente. Permite cambiar usuario, nombre completo,
     * email, contraseña y rol.
     */
    @Transactional
    public UsuarioDTO update(Long id, UsuarioCreateDTO dto) {
        Usuario u = usuarioRepo.findById(id)
            .orElseThrow(() -> new NotFoundException("Usuario con id " + id + " no encontrado"));

        // Validar unicidad si cambian usuario o email
        if (!u.getUsuario().equals(dto.getUsuario())
            && usuarioRepo.findByUsuarioAndEmpresaId(dto.getUsuario(), u.getEmpresa().getId()).isPresent()) {
            throw new BadRequestException("Ya existe otro usuario con ese nombre en la empresa");
        }
        if (!u.getEmail().equals(dto.getEmail())
            && usuarioRepo.findByEmailAndEmpresaId(dto.getEmail(), u.getEmpresa().getId()).isPresent()) {
            throw new BadRequestException("Ya existe otro usuario con ese email en la empresa");
        }

        // Aplicar cambios
        u.setUsuario(dto.getUsuario());
        u.setNombreCompleto(dto.getNombreCompleto());
        u.setEmail(dto.getEmail());
        if (dto.getContrasena() != null && !dto.getContrasena().isBlank()) {
            u.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        }
        // opcional: permitir cambiar de rol
        if (!u.getRol().getId().equals(dto.getRolId())) {
            Rol rol = rolRepo.findById(dto.getRolId())
                             .orElseThrow(() -> new NotFoundException("Rol no encontrado: " + dto.getRolId()));
            u.setRol(rol);
        }

        Usuario saved = usuarioRepo.save(u);
        return toDTO(saved);
    }

    /**
     * Activa un usuario (set activo = true).
     */
    @Transactional
    public void activate(Long id) {
        Usuario u = usuarioRepo.findById(id)
            .orElseThrow(() -> new NotFoundException("Usuario con id " + id + " no encontrado"));
        u.setActivo(true);
        usuarioRepo.save(u);
    }

    /**
     * Desactiva un usuario (set activo = false).
     */
    @Transactional
    public void deactivate(Long id) {
        Usuario u = usuarioRepo.findById(id)
            .orElseThrow(() -> new NotFoundException("Usuario con id " + id + " no encontrado"));
        u.setActivo(false);
        usuarioRepo.save(u);
    }

    private UsuarioDTO toDTO(Usuario u) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(u.getId());
        dto.setUsuario(u.getUsuario());
        dto.setNombreCompleto(u.getNombreCompleto());
        dto.setEmail(u.getEmail());
        dto.setRolId(u.getRol().getId());
        dto.setEmpresaId(u.getEmpresa().getId());
        dto.setFechaUltimoAcceso(u.getFechaUltimoAcceso());
        dto.setActivo(u.getActivo());
        return dto;
    }
}
