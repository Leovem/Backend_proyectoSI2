package com.activofijo.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FacturaDTO {

    private Long id;
    private String numero;
    private LocalDate fecha;
    private BigDecimal total;
    private String monedaCodigo;
    private String tipoPago;
    private String observaciones;

    private Long proveedorId;
    private String proveedorNombre;

    private Long usuarioId;
    private String usuarioNombre;

    private Long ordenCompraId;
    private String ordenCompraNumero;

    private Long presupuestoId;
    private String presupuestoNombre;

    private Long cuentaContableId;
    private String cuentaContableNombre;

    private Long empresaId;

    public FacturaDTO(
        Long id,
        String numero,
        LocalDate fecha,
        BigDecimal total,
        String monedaCodigo,
        String tipoPago,
        String observaciones,
        Long proveedorId,
        String proveedorNombre,
        Long usuarioId,
        String usuarioNombre,
        Long ordenCompraId,
        String ordenCompraNumero,
        Long presupuestoId,
        String presupuestoNombre,
        Long cuentaContableId,
        String cuentaContableNombre,
        Long empresaId
    ) {
        this.id = id;
        this.numero = numero;
        this.fecha = fecha;
        this.total = total;
        this.monedaCodigo = monedaCodigo;
        this.tipoPago = tipoPago;
        this.observaciones = observaciones;

        this.proveedorId = proveedorId;
        this.proveedorNombre = proveedorNombre;

        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;

        this.ordenCompraId = ordenCompraId;
        this.ordenCompraNumero = ordenCompraNumero;

        this.presupuestoId = presupuestoId;
        this.presupuestoNombre = presupuestoNombre;

        this.cuentaContableId = cuentaContableId;
        this.cuentaContableNombre = cuentaContableNombre;

        this.empresaId = empresaId;
    }


    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getProveedorNombre() {
        return proveedorNombre;
    }

    public void setProveedorNombre(String proveedorNombre) {
        this.proveedorNombre = proveedorNombre;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public Long getOrdenCompraId() {
        return ordenCompraId;
    }

    public void setOrdenCompraId(Long ordenCompraId) {
        this.ordenCompraId = ordenCompraId;
    }

    public String getOrdenCompraNumero() {
        return ordenCompraNumero;
    }

    public void setOrdenCompraNumero(String ordenCompraNumero) {
        this.ordenCompraNumero = ordenCompraNumero;
    }

    public Long getPresupuestoId() {
        return presupuestoId;
    }

    public void setPresupuestoId(Long presupuestoId) {
        this.presupuestoId = presupuestoId;
    }

    public String getPresupuestoNombre() {
        return presupuestoNombre;
    }

    public void setPresupuestoNombre(String presupuestoNombre) {
        this.presupuestoNombre = presupuestoNombre;
    }

    public Long getCuentaContableId() {
        return cuentaContableId;
    }

    public void setCuentaContableId(Long cuentaContableId) {
        this.cuentaContableId = cuentaContableId;
    }

    public String getCuentaContableNombre() {
        return cuentaContableNombre;
    }

    public void setCuentaContableNombre(String cuentaContableNombre) {
        this.cuentaContableNombre = cuentaContableNombre;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getMonedaCodigo() {
        return monedaCodigo;
    }

    public void setMonedaCodigo(String monedaCodigo) {
        this.monedaCodigo = monedaCodigo;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
