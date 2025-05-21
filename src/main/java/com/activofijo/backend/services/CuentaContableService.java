package com.activofijo.backend.services;

import com.activofijo.backend.dto.CuentaContableCreateDTO;
import com.activofijo.backend.dto.CuentaContableDTO;
import com.activofijo.backend.models.CuentaContable;
import com.activofijo.backend.models.Empresa;
import com.activofijo.backend.models.TipoCuenta;
import com.activofijo.backend.repository.CuentaContableRepository;
import com.activofijo.backend.repository.EmpresaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaContableService {

    private final CuentaContableRepository cuentaRepository;
    private final EmpresaRepository empresaRepository;

    public CuentaContableService(CuentaContableRepository cuentaRepository,
                                  EmpresaRepository empresaRepository) {
        this.cuentaRepository = cuentaRepository;
        this.empresaRepository = empresaRepository;
    }

    @Transactional
    public CuentaContableDTO crear(CuentaContableCreateDTO dto) {
        if (cuentaRepository.existsByCodigoAndEmpresa_Id(dto.getCodigo(), dto.getEmpresaId())) {
            throw new IllegalArgumentException("Ya existe una cuenta contable con ese código para esta empresa.");
        }

        Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada."));

        CuentaContable cuenta = new CuentaContable();
        cuenta.setCodigo(dto.getCodigo());
        cuenta.setNombre(dto.getNombre());
        cuenta.setTipo(TipoCuenta.valueOf(dto.getTipo()));
        cuenta.setEmpresa(empresa);

        CuentaContable guardada = cuentaRepository.save(cuenta);
        return toDTO(guardada);
    }

    @Transactional(readOnly = true)
    public List<CuentaContableDTO> listarPorEmpresa(Long empresaId) {
        return cuentaRepository.findAllByEmpresa_Id(empresaId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CuentaContableDTO actualizar(Long id, CuentaContableCreateDTO dto) {
        CuentaContable cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta contable no encontrada."));

        if (!cuenta.getEmpresa().getId().equals(dto.getEmpresaId())) {
            throw new IllegalArgumentException("No tiene permiso para modificar esta cuenta.");
        }

        if (!cuenta.getCodigo().equals(dto.getCodigo()) &&
                cuentaRepository.existsByCodigoAndEmpresa_Id(dto.getCodigo(), dto.getEmpresaId())) {
            throw new IllegalArgumentException("Ya existe una cuenta con ese código en la empresa.");
        }

        cuenta.setCodigo(dto.getCodigo());
        cuenta.setNombre(dto.getNombre());
        cuenta.setTipo(TipoCuenta.valueOf(dto.getTipo()));

        CuentaContable actualizada = cuentaRepository.save(cuenta);
        return toDTO(actualizada);
    }

    @Transactional
    public void eliminar(Long id, Long empresaId) {
        CuentaContable cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta contable no encontrada."));

        if (!cuenta.getEmpresa().getId().equals(empresaId)) {
            throw new IllegalArgumentException("No tiene permiso para eliminar esta cuenta.");
        }

        cuentaRepository.delete(cuenta);
    }

    private CuentaContableDTO toDTO(CuentaContable cuenta) {
        CuentaContableDTO dto = new CuentaContableDTO();
        dto.setId(cuenta.getId());
        dto.setCodigo(cuenta.getCodigo());
        dto.setNombre(cuenta.getNombre());
        dto.setTipo(cuenta.getTipo().name());
        dto.setEmpresaId(cuenta.getEmpresa().getId());
        return dto;
    }
}
