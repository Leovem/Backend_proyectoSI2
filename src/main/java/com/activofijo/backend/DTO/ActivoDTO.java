package com.activofijo.backend.dto;

import jakarta.json.JsonObject;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ActivoDTO {

    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;

    private Long tipoId;
    private Long metodoDepreciacionId;
    private Long tipoDepreciacionId;
    private Long grupoId;
    private Long clasificacionId;
    private Long marcaId;
    private Long modeloId;
    private Long contratoId;
    private Long ubicacionId;
    private Long facturaId;
    private Long cuentaContableId;

    private String tipoNombre;
    private String metodoDepreciacionNombre;
    private String tipoDepreciacionNombre;
    private String grupoNombre;
    private String clasificacionNombre;
    private String marcaNombre;
    private String modeloNombre;
    private String contratoNombre;
    private String ubicacionNombre;
    private String facturaCodigo;
    private String cuentaContableNombre;

    private Long empresaId;
    private BigDecimal valorInicial;
    private String monedaCodigo;
    private LocalDate fechaAdquisicion;

    private String estado;
    private Boolean activo;
    private JsonObject nombreI18n;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getTipoId() {
        return tipoId;
    }

    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }

    public Long getMetodoDepreciacionId() {
        return metodoDepreciacionId;
    }

    public void setMetodoDepreciacionId(Long metodoDepreciacionId) {
        this.metodoDepreciacionId = metodoDepreciacionId;
    }

    public Long getTipoDepreciacionId() {
        return tipoDepreciacionId;
    }

    public void setTipoDepreciacionId(Long tipoDepreciacionId) {
        this.tipoDepreciacionId = tipoDepreciacionId;
    }

    public Long getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Long grupoId) {
        this.grupoId = grupoId;
    }

    public Long getClasificacionId() {
        return clasificacionId;
    }

    public void setClasificacionId(Long clasificacionId) {
        this.clasificacionId = clasificacionId;
    }

    public Long getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(Long marcaId) {
        this.marcaId = marcaId;
    }

    public Long getModeloId() {
        return modeloId;
    }

    public void setModeloId(Long modeloId) {
        this.modeloId = modeloId;
    }

    public Long getContratoId() {
        return contratoId;
    }

    public void setContratoId(Long contratoId) {
        this.contratoId = contratoId;
    }

    public Long getUbicacionId() {
        return ubicacionId;
    }

    public void setUbicacionId(Long ubicacionId) {
        this.ubicacionId = ubicacionId;
    }

    public Long getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Long facturaId) {
        this.facturaId = facturaId;
    }

    public Long getCuentaContableId() {
        return cuentaContableId;
    }

    public void setCuentaContableId(Long cuentaContableId) {
        this.cuentaContableId = cuentaContableId;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public BigDecimal getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(BigDecimal valorInicial) {
        this.valorInicial = valorInicial;
    }

    public String getMonedaCodigo() {
        return monedaCodigo;
    }

    public void setMonedaCodigo(String monedaCodigo) {
        this.monedaCodigo = monedaCodigo;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public JsonObject getNombreI18n() {
        return nombreI18n;
    }

    public void setNombreI18n(JsonObject nombreI18n) {
        this.nombreI18n = nombreI18n;
    }

    public String getTipoNombre() {
        return tipoNombre;
    }

    public void setTipoNombre(String tipoNombre) {
        this.tipoNombre = tipoNombre;
    }

    public String getMetodoDepreciacionNombre() {
        return metodoDepreciacionNombre;
    }

    public void setMetodoDepreciacionNombre(String metodoDepreciacionNombre) {
        this.metodoDepreciacionNombre = metodoDepreciacionNombre;
    }

    public String getTipoDepreciacionNombre() {
        return tipoDepreciacionNombre;
    }

    public void setTipoDepreciacionNombre(String tipoDepreciacionNombre) {
        this.tipoDepreciacionNombre = tipoDepreciacionNombre;
    }

    public String getGrupoNombre() {
        return grupoNombre;
    }

    public void setGrupoNombre(String grupoNombre) {
        this.grupoNombre = grupoNombre;
    }

    public String getClasificacionNombre() {
        return clasificacionNombre;
    }

    public void setClasificacionNombre(String clasificacionNombre) {
        this.clasificacionNombre = clasificacionNombre;
    }

    public String getMarcaNombre() {
        return marcaNombre;
    }

    public void setMarcaNombre(String marcaNombre) {
        this.marcaNombre = marcaNombre;
    }

    public String getModeloNombre() {
        return modeloNombre;
    }

    public void setModeloNombre(String modeloNombre) {
        this.modeloNombre = modeloNombre;
    }

    public String getContratoNombre() {
        return contratoNombre;
    }

    public void setContratoNombre(String contratoNombre) {
        this.contratoNombre = contratoNombre;
    }

    public String getUbicacionNombre() {
        return ubicacionNombre;
    }

    public void setUbicacionNombre(String ubicacionNombre) {
        this.ubicacionNombre = ubicacionNombre;
    }

    public String getFacturaCodigo() {
        return facturaCodigo;
    }

    public void setFacturaCodigo(String facturaCodigo) {
        this.facturaCodigo = facturaCodigo;
    }

    public String getCuentaContableNombre() {
        return cuentaContableNombre;
    }

    public void setCuentaContableNombre(String cuentaContableNombre) {
        this.cuentaContableNombre = cuentaContableNombre;
    }

}
