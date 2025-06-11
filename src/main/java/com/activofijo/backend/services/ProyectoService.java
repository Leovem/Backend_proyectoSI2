package com.activofijo.backend.services;

import com.activofijo.backend.dto.ProyectoCreateDTO;
import com.activofijo.backend.dto.ProyectoDTO;
import com.activofijo.backend.exception.BadRequestException;
import com.activofijo.backend.models.Empresa;
import com.activofijo.backend.models.Proyecto;
import com.activofijo.backend.models.Usuario;
import com.activofijo.backend.repository.EmpresaRepository;
import com.activofijo.backend.repository.ProyectoRepository;
import com.activofijo.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepo;
    private final AuditoriaService auditoriaService;
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ProyectoService.class);

    public ProyectoService(ProyectoRepository proyectoRepository, EmpresaRepository empresaRepository,
                           UsuarioRepository usuarioRepo,
                           AuditoriaService auditoriaService) {
        this.proyectoRepository = proyectoRepository;
        this.empresaRepository = empresaRepository;
        this.usuarioRepo = usuarioRepo;
        this.auditoriaService = auditoriaService;
    }

    @Transactional
    public ProyectoDTO crearProyecto(ProyectoCreateDTO dto, String ipCliente, String username) {
        if (proyectoRepository.existsByNombreAndEmpresa_Id(dto.getNombre(), dto.getEmpresaId())) {
            throw new IllegalArgumentException("Ya existe un proyecto con ese nombre para esta empresa.");
        }
        Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada con id: " + dto.getEmpresaId()));

        Usuario usuarioCreador = usuarioRepo.findByUsuarioAndEmpresaId(username, dto.getEmpresaId())
                .orElseThrow(() -> new BadRequestException("Usuario creador no encontrado"));

        Long idCreador = usuarioCreador.getId();


        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(dto.getNombre());
        proyecto.setDescripcion(dto.getDescripcion());
        proyecto.setEmpresa(empresa);

        Proyecto saved = proyectoRepository.save(proyecto);

        try {
            auditoriaService.registrarOperacion(
                    "proyectos", // 👈 tabla correcta
                    "INSERT",
                    null, // antes del insert no existía
                    proyecto, // después
                    saved.getId(), // id del nuevo rol creado
                    idCreador,
                    dto.getEmpresaId(),
                    "Creación de rol",
                    "Se creó el proyecto con nombre=" + proyecto.getNombre() + " por el usuario " + username,
                    ipCliente);
        } catch (Exception e) {
            logger.warn("❌ Error registrando auditoría de creación de rol: {}", e.getMessage());
        }
        // Aquí conviertes la entidad Proyecto a DTO antes de devolver
        return new ProyectoDTO(
                saved.getId(),
                saved.getNombre(),
                saved.getDescripcion(),
                saved.getEmpresa().getId());
    }

    @Transactional(readOnly = true)
    public List<ProyectoDTO> listarProyectosPorEmpresa(Long empresaId) {
        return proyectoRepository.findAllByEmpresa_Id(empresaId).stream()
                .map(p -> new ProyectoDTO(p.getId(), p.getNombre(), p.getDescripcion(), p.getEmpresa().getId()))
                .collect(Collectors.toList());

    }

    @Transactional
    public ProyectoDTO actualizarProyecto(Long id, ProyectoCreateDTO dto) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado con id: " + id));

        if (!proyecto.getEmpresa().getId().equals(dto.getEmpresaId())) {
            throw new IllegalArgumentException("No tiene permiso para modificar este proyecto.");
        }

        if (!proyecto.getNombre().equals(dto.getNombre()) &&
                proyectoRepository.existsByNombreAndEmpresa_Id(dto.getNombre(), dto.getEmpresaId())) {
            throw new IllegalArgumentException("Ya existe un proyecto con ese nombre para esta empresa.");
        }

        proyecto.setNombre(dto.getNombre());
        proyecto.setDescripcion(dto.getDescripcion());

        Proyecto actualizado = proyectoRepository.save(proyecto);

        return new ProyectoDTO(
                actualizado.getId(),
                actualizado.getNombre(),
                actualizado.getDescripcion(),
                actualizado.getEmpresa().getId());

    }

    @Transactional
    public void eliminarProyecto(Long id, Long empresaId) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado con id: " + id));

        if (!proyecto.getEmpresa().getId().equals(empresaId)) {
            throw new IllegalArgumentException("No tiene permiso para eliminar este proyecto.");
        }

        proyectoRepository.delete(proyecto);
    }
}
