package com.activofijo.backend.services;

import com.activofijo.backend.dto.ActivoCreateDTO;
import com.activofijo.backend.dto.ActivoDTO;
import com.activofijo.backend.models.*;
import com.activofijo.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivoService {

    private final ActivoRepository activoRepository;
    private final EmpresaRepository empresaRepository;
    private final TipoActivoRepository tipoActivoRepository;
    private final MetodoDepreciacionRepository metodoDepreciacionRepository;
    private final TipoDepreciacionRepository tipoDepreciacionRepository;
    private final GrupoActivoRepository grupoActivoRepository;
    private final ClasificacionActivoRepository clasificacionActivoRepository;
    private final MarcaRepository marcaRepository;
    private final ModeloRepository modeloRepository;
    private final TipoContratoRepository contratoRepository;
    private final UbicacionRepository ubicacionRepository;
    private final FacturaRepository facturaRepository;
    private final CuentaContableRepository cuentaContableRepository;
    private final MonedaRepository monedaRepository;

    public ActivoService(ActivoRepository activoRepository,
                         EmpresaRepository empresaRepository,
                         TipoActivoRepository tipoActivoRepository,
                         MetodoDepreciacionRepository metodoDepreciacionRepository,
                         TipoDepreciacionRepository tipoDepreciacionRepository,
                         GrupoActivoRepository grupoActivoRepository,
                         ClasificacionActivoRepository clasificacionActivoRepository,
                         MarcaRepository marcaRepository,
                         ModeloRepository modeloRepository,
                         TipoContratoRepository contratoRepository,
                         UbicacionRepository ubicacionRepository,
                         FacturaRepository facturaRepository,
                         CuentaContableRepository cuentaContableRepository,
                         MonedaRepository monedaRepository) {
        this.activoRepository = activoRepository;
        this.empresaRepository = empresaRepository;
        this.tipoActivoRepository = tipoActivoRepository;
        this.metodoDepreciacionRepository = metodoDepreciacionRepository;
        this.tipoDepreciacionRepository = tipoDepreciacionRepository;
        this.grupoActivoRepository = grupoActivoRepository;
        this.clasificacionActivoRepository = clasificacionActivoRepository;
        this.marcaRepository = marcaRepository;
        this.modeloRepository = modeloRepository;
        this.contratoRepository = contratoRepository;
        this.ubicacionRepository = ubicacionRepository;
        this.facturaRepository = facturaRepository;
        this.cuentaContableRepository = cuentaContableRepository;
        this.monedaRepository = monedaRepository;
    }

    @Transactional
    public ActivoDTO crear(ActivoCreateDTO dto) {
        if (activoRepository.existsByCodigoAndEmpresa_Id(dto.getCodigo(), dto.getEmpresaId())) {
            throw new IllegalArgumentException("Ya existe un activo con ese código para esta empresa.");
        }

        Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada."));

        TipoActivo tipo = tipoActivoRepository.findById(dto.getTipoId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de activo no encontrado."));

        Activo activo = new Activo();
        activo.setCodigo(dto.getCodigo());
        activo.setNombre(dto.getNombre());
        activo.setDescripcion(dto.getDescripcion());
        activo.setTipo(tipo);
        activo.setEmpresa(empresa);
        activo.setValorInicial(dto.getValorInicial());
        activo.setFechaAdquisicion(dto.getFechaAdquisicion());
        activo.setEstado(dto.getEstado());
        activo.setActivo(dto.getActivo());
        activo.setNombreI18n(dto.getNombreI18n());

        if (dto.getMetodoDepreciacionId() != null)
            activo.setMetodoDepreciacion(metodoDepreciacionRepository.findById(dto.getMetodoDepreciacionId())
                    .orElseThrow(() -> new IllegalArgumentException("Método de depreciación no encontrado.")));

        if (dto.getTipoDepreciacionId() != null)
            activo.setTipoDepreciacion(tipoDepreciacionRepository.findById(dto.getTipoDepreciacionId())
                    .orElseThrow(() -> new IllegalArgumentException("Tipo de depreciación no encontrado.")));

        if (dto.getGrupoId() != null)
            activo.setGrupo(grupoActivoRepository.findById(dto.getGrupoId())
                    .orElseThrow(() -> new IllegalArgumentException("Grupo de activo no encontrado.")));

        if (dto.getClasificacionId() != null)
            activo.setClasificacion(clasificacionActivoRepository.findById(dto.getClasificacionId())
                    .orElseThrow(() -> new IllegalArgumentException("Clasificación no encontrada.")));

        if (dto.getMarcaId() != null)
            activo.setMarca(marcaRepository.findById(dto.getMarcaId())
                    .orElseThrow(() -> new IllegalArgumentException("Marca no encontrada.")));

        if (dto.getModeloId() != null)
            activo.setModelo(modeloRepository.findById(dto.getModeloId())
                    .orElseThrow(() -> new IllegalArgumentException("Modelo no encontrado.")));

        if (dto.getContratoId() != null)
            activo.setContrato(contratoRepository.findById(dto.getContratoId())
                    .orElseThrow(() -> new IllegalArgumentException("Contrato no encontrado.")));

        if (dto.getUbicacionId() != null)
            activo.setUbicacion(ubicacionRepository.findById(dto.getUbicacionId())
                    .orElseThrow(() -> new IllegalArgumentException("Ubicación no encontrada.")));

        if (dto.getFacturaId() != null)
            activo.setFactura(facturaRepository.findById(dto.getFacturaId())
                    .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada.")));

        if (dto.getCuentaContableId() != null)
            activo.setCuentaContable(cuentaContableRepository.findById(dto.getCuentaContableId())
                    .orElseThrow(() -> new IllegalArgumentException("Cuenta contable no encontrada.")));

        activo.setMoneda(monedaRepository.findById(dto.getMonedaCodigo())
                .orElseThrow(() -> new IllegalArgumentException("Moneda no encontrada.")));

        Activo saved = activoRepository.save(activo);
        return toDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<ActivoDTO> listarPorEmpresa(Long empresaId) {
        return activoRepository.findAllByEmpresa_Id(empresaId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ActivoDTO toDTO(Activo a) {
    ActivoDTO dto = new ActivoDTO();
    dto.setId(a.getId());
    dto.setCodigo(a.getCodigo());
    dto.setNombre(a.getNombre());
    dto.setDescripcion(a.getDescripcion());
    dto.setEmpresaId(a.getEmpresa().getId());
    dto.setValorInicial(a.getValorInicial());
    dto.setMonedaCodigo(a.getMoneda().getCodigo());
    dto.setFechaAdquisicion(a.getFechaAdquisicion());
    dto.setEstado(a.getEstado());
    dto.setActivo(a.getActivo());
    dto.setNombreI18n(a.getNombreI18n());

    if (a.getTipo() != null) {
        dto.setTipoId(a.getTipo().getId());
        dto.setTipoNombre(a.getTipo().getNombre());
    }
    if (a.getMetodoDepreciacion() != null) {
        dto.setMetodoDepreciacionId(a.getMetodoDepreciacion().getId());
        dto.setMetodoDepreciacionNombre(a.getMetodoDepreciacion().getNombre());
    }
    if (a.getTipoDepreciacion() != null) {
        dto.setTipoDepreciacionId(a.getTipoDepreciacion().getId());
        dto.setTipoDepreciacionNombre(a.getTipoDepreciacion().getNombre());
    }
    if (a.getGrupo() != null) {
        dto.setGrupoId(a.getGrupo().getId());
        dto.setGrupoNombre(a.getGrupo().getNombre());
    }
    if (a.getClasificacion() != null) {
        dto.setClasificacionId(a.getClasificacion().getId());
        dto.setClasificacionNombre(a.getClasificacion().getNombre());
    }
    if (a.getMarca() != null) {
        dto.setMarcaId(a.getMarca().getId());
        dto.setMarcaNombre(a.getMarca().getNombre());
    }
    if (a.getModelo() != null) {
        dto.setModeloId(a.getModelo().getId());
        dto.setModeloNombre(a.getModelo().getNombre());
    }
    if (a.getContrato() != null) {
        dto.setContratoId(a.getContrato().getId());
        dto.setContratoNombre(a.getContrato().getNombre());
    }
    if (a.getUbicacion() != null) {
        dto.setUbicacionId(a.getUbicacion().getId());
        dto.setUbicacionNombre(a.getUbicacion().getNombre());
    }
    if (a.getFactura() != null) {
        dto.setFacturaId(a.getFactura().getId());
        dto.setFacturaCodigo(a.getFactura().getNumero());
    }
    if (a.getCuentaContable() != null) {
        dto.setCuentaContableId(a.getCuentaContable().getId());
        dto.setCuentaContableNombre(a.getCuentaContable().getNombre());
    }

    return dto;
}

}
