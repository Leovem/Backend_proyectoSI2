package com.activofijo.backend.services;

import com.activofijo.backend.dto.ProveedorCreateDTO;
import com.activofijo.backend.dto.ProveedorDTO;
import com.activofijo.backend.models.Empresa;
import com.activofijo.backend.models.Proveedor;
import com.activofijo.backend.repository.EmpresaRepository;
import com.activofijo.backend.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final EmpresaRepository empresaRepository;

    public ProveedorService(ProveedorRepository proveedorRepository, EmpresaRepository empresaRepository) {
        this.proveedorRepository = proveedorRepository;
        this.empresaRepository = empresaRepository;
    }

    public ProveedorDTO crearProveedor(ProveedorCreateDTO dto) {
        Optional<Empresa> empresaOpt = empresaRepository.findById(dto.getEmpresaId());
        if (empresaOpt.isEmpty()) {
            throw new IllegalArgumentException("Empresa no encontrada con ID: " + dto.getEmpresaId());
        }

        Proveedor proveedor = new Proveedor(
                dto.getNombre(),
                dto.getContacto(),
                dto.getEmail(),
                dto.getTelefono(),
                dto.getDireccion(),
                empresaOpt.get()
        );

        Proveedor guardado = proveedorRepository.save(proveedor);
        return toDTO(guardado);
    }

    public List<ProveedorDTO> listarProveedoresPorEmpresa(Long empresaId) {
        return proveedorRepository.findByEmpresaId(empresaId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProveedorDTO actualizarProveedor(Long id, ProveedorCreateDTO dto) {
        Optional<Proveedor> proveedorOpt = proveedorRepository.findById(id);
        if (proveedorOpt.isEmpty()) {
            throw new IllegalArgumentException("Proveedor no encontrado con ID: " + id);
        }

        Proveedor proveedor = proveedorOpt.get();
        if (!proveedor.getEmpresa().getId().equals(dto.getEmpresaId())) {
            throw new IllegalArgumentException("No autorizado para modificar este proveedor.");
        }

        proveedor.setNombre(dto.getNombre());
        proveedor.setContacto(dto.getContacto());
        proveedor.setEmail(dto.getEmail());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setDireccion(dto.getDireccion());

        Proveedor actualizado = proveedorRepository.save(proveedor);
        return toDTO(actualizado);
    }

    public void eliminarProveedor(Long id, Long empresaId) {
        Optional<Proveedor> proveedorOpt = proveedorRepository.findById(id);
        if (proveedorOpt.isEmpty()) {
            throw new IllegalArgumentException("Proveedor no encontrado con ID: " + id);
        }

        Proveedor proveedor = proveedorOpt.get();
        if (!proveedor.getEmpresa().getId().equals(empresaId)) {
            throw new IllegalArgumentException("No autorizado para eliminar este proveedor.");
        }

        proveedorRepository.delete(proveedor);
    }

    private ProveedorDTO toDTO(Proveedor proveedor) {
        ProveedorDTO dto = new ProveedorDTO();
        dto.setId(proveedor.getId());
        dto.setNombre(proveedor.getNombre());
        dto.setContacto(proveedor.getContacto());
        dto.setEmail(proveedor.getEmail());
        dto.setTelefono(proveedor.getTelefono());
        dto.setDireccion(proveedor.getDireccion());
        return dto;
    }
}
