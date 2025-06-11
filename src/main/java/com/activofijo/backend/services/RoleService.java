package com.activofijo.backend.services;

import com.activofijo.backend.dto.PrivilegioDTO;
import com.activofijo.backend.dto.RolDTO;
import com.activofijo.backend.exception.BadRequestException;
import com.activofijo.backend.exception.NotFoundException;
import com.activofijo.backend.models.Empresa;
import com.activofijo.backend.models.Privilegio;
import com.activofijo.backend.models.Rol;
import com.activofijo.backend.models.Usuario;
import com.activofijo.backend.repository.EmpresaRepository;
import com.activofijo.backend.repository.PrivilegioRepository;
import com.activofijo.backend.repository.RolRepository;
import com.activofijo.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RolRepository rolRepo;
    private final EmpresaRepository empresaRepo;
    private final PrivilegioRepository privilegioRepo;
    private final UsuarioRepository usuarioRepo;
    private final AuditoriaService auditoriaService;
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RoleService.class);

    public RoleService(RolRepository rolRepo,
            EmpresaRepository empresaRepo,
            PrivilegioRepository privilegioRepo,
            UsuarioRepository usuarioRepo,
            AuditoriaService auditoriaService) {
        this.rolRepo = rolRepo;
        this.empresaRepo = empresaRepo;
        this.privilegioRepo = privilegioRepo;
        this.usuarioRepo = usuarioRepo;
        this.auditoriaService = auditoriaService;
    }

    @Transactional(readOnly = true)
    public List<RolDTO> listByEmpresa(Long empresaId) {
        empresaRepo.findById(empresaId)
                .orElseThrow(() -> new NotFoundException("Empresa no encontrada: " + empresaId));

        return rolRepo.findByEmpresaId(empresaId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public RolDTO create(RolDTO dto, List<Long> privilegioIds, String ipCliente, String username) {
        if (rolRepo.findByNombreAndEmpresaId(dto.getNombre(), dto.getEmpresaId()).isPresent()) {
            throw new BadRequestException("Ya existe un rol con ese nombre en la empresa");
        }

        Empresa empresa = empresaRepo.findById(dto.getEmpresaId())
                .orElseThrow(() -> new NotFoundException("Empresa no encontrada: " + dto.getEmpresaId()));

        Usuario usuarioCreador = usuarioRepo.findByUsuarioAndEmpresaId(username, dto.getEmpresaId())
                .orElseThrow(() -> new BadRequestException("Usuario creador no encontrado"));

        Long idCreador = usuarioCreador.getId();

        Rol rol = new Rol();
        rol.setNombre(dto.getNombre());
        rol.setEmpresa(empresa);

        if (privilegioIds != null && !privilegioIds.isEmpty()) {
            List<Privilegio> privilegios = privilegioRepo.findAllById(privilegioIds);
            if (privilegios.size() != privilegioIds.size()) {
                throw new BadRequestException("Alguno de los privilegios no existe");
            }
            // aquí usamos HashSet para JDK 8+:
            Set<Privilegio> setPriv = new HashSet<>(privilegios);
            rol.setPrivilegios(setPriv);
        }

        Rol saved = rolRepo.save(rol);

        try {
            auditoriaService.registrarOperacion(
                    "roles", // 👈 tabla correcta
                    "INSERT",
                    null, // antes del insert no existía
                    rol, // después
                    saved.getId(), // id del nuevo rol creado
                    idCreador,
                    dto.getEmpresaId(),
                    "Creación de rol",
                    "Se creó el rol con nombre=" + rol.getNombre() + "por el usuario " + username,
                    ipCliente);
        } catch (Exception e) {
            logger.warn("❌ Error registrando auditoría de creación de rol: {}", e.getMessage());
        }

        return toDTO(saved);
    }

    @Transactional
    public RolDTO update(Long rolId, RolDTO dto, List<Long> privilegioIds) {
        Rol rol = rolRepo.findById(rolId)
                .orElseThrow(() -> new NotFoundException("Rol no encontrado: " + rolId));

        if (!rol.getNombre().equals(dto.getNombre()) &&
                rolRepo.findByNombreAndEmpresaId(dto.getNombre(), rol.getEmpresa().getId()).isPresent()) {
            throw new BadRequestException("Ya existe un rol con ese nombre en la empresa");
        }

        rol.setNombre(dto.getNombre());

        if (privilegioIds != null) {
            List<Privilegio> privilegios = privilegioRepo.findAllById(privilegioIds);
            if (privilegios.size() != privilegioIds.size()) {
                throw new BadRequestException("Alguno de los privilegios no existe");
            }
            rol.setPrivilegios(new HashSet<>(privilegios));
        }

        Rol updated = rolRepo.save(rol);
        return toDTO(updated);
    }

    @Transactional
    public void delete(Long rolId) {
        if (!rolRepo.existsById(rolId)) {
            throw new NotFoundException("Rol no encontrado: " + rolId);
        }
        rolRepo.deleteById(rolId);
    }

    @Transactional
    public RolDTO actualizarPrivilegios(Long rolId, List<Long> privilegioIds) {
        Rol rol = rolRepo.findById(rolId)
                .orElseThrow(() -> new NotFoundException("Rol no encontrado"));

        rol.getPrivilegios().clear(); // elimina todos los actuales

        List<Privilegio> nuevos = privilegioRepo.findAllById(privilegioIds);
        rol.getPrivilegios().addAll(nuevos); // agrega los nuevos

        rolRepo.save(rol);
        return toDTO(rol); // convierte a RolDTO
    }


    private RolDTO toDTO(Rol rol) {
        RolDTO dto = new RolDTO();
        dto.setId(rol.getId());
        dto.setNombre(rol.getNombre());
        dto.setEmpresaId(rol.getEmpresa().getId());

        // IDs de los privilegios
        List<Long> privilegioIds = rol.getPrivilegios().stream()
                .map(Privilegio::getId)
                .toList();
        dto.setPrivilegioIds(privilegioIds);

        // Lista detallada de privilegios
        List<PrivilegioDTO> privilegios = rol.getPrivilegios().stream()
                .map(p -> new PrivilegioDTO(p.getId(), p.getNombre()))
                .toList();
        dto.setPrivilegios(privilegios);

        return dto;
    }

}
