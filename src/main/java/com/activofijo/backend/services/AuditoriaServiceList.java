package com.activofijo.backend.services;

import com.activofijo.backend.dto.AuditoriaDTO;
import com.activofijo.backend.models.Auditoria;
import com.activofijo.backend.repository.AuditoriaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditoriaServiceList {

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaServiceList(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    public List<AuditoriaDTO> listarPorEmpresa(Long empresaId) {
        return auditoriaRepository.findByEmpresaIdOrderByFechaClienteDesc(empresaId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private AuditoriaDTO toDTO(Auditoria auditoria) {
        return new AuditoriaDTO(
                auditoria.getId(),
                auditoria.getTablaAfectada(),
                auditoria.getRegistroId(),
                auditoria.getOperacion(),
                auditoria.getValoresAnteriores(),
                auditoria.getValoresNuevos(),
                auditoria.getUsuarioId(),
                auditoria.getEmpresaId(),
                auditoria.getAccion(),
                auditoria.getDescripcion(),
                auditoria.getIpCliente(),
                auditoria.getFechaCliente()
        );
    }
}
