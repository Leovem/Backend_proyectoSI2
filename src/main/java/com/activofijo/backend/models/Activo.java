package com.activofijo.backend.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "activos")
public class Activo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String codigo;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "tipo_id", nullable = false)
    private TipoActivo tipoActivo;

    @ManyToOne
    @JoinColumn(name = "metodo_depreciacion_id")
    private MetodoDepreciacion metodoDepreciacion;

    @ManyToOne
    @JoinColumn(name = "tipo_depreciacion_id")
    private TipoDepreciacion tipoDepreciacion;

    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private GrupoActivo grupoActivo;

    @ManyToOne
    @JoinColumn(name = "clasificacion_id")
    private ClasificacionActivo clasificacionActivo;

    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "modelo_id")
    private Modelo modelo;

    @ManyToOne
    @JoinColumn(name = "contrato_id")
    private TipoContrato tipoContrato;

    @ManyToOne
    @JoinColumn(name = "ubicacion_id")
    private Ubicacion ubicacion;

    @ManyToOne
    @JoinColumn(name = "factura_id")
    private Factura factura;

    @ManyToOne
    @JoinColumn(name = "cuenta_contable_id")
    private CuentaContable cuentaContable;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @Column(nullable = false)
    private BigDecimal valorInicial;

    @ManyToOne
    @JoinColumn(name = "moneda")
    private Moneda moneda;

    @Column(nullable = false)
    private LocalDate fechaAdquisicion;

    @ManyToOne
    @JoinColumn(name = "estado", nullable = false)
    private EstadoActivo estado;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(columnDefinition = "jsonb")
    private String nombreI18n;

    @PrePersist
    @PreUpdate
    private void checkCodigoFormat() {
        if (codigo != null && !codigo.matches("^[A-Z]{3}-\\d+$")) {
            throw new IllegalArgumentException("El formato del código es incorrecto.");
        }
    }

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

    public TipoActivo getTipoActivo() {
        return tipoActivo;
    }

    public void setTipoActivo(TipoActivo tipoActivo) {
        this.tipoActivo = tipoActivo;
    }

    public MetodoDepreciacion getMetodoDepreciacion() {
        return metodoDepreciacion;
    }

    public void setMetodoDepreciacion(MetodoDepreciacion metodoDepreciacion) {
        this.metodoDepreciacion = metodoDepreciacion;
    }

    public TipoDepreciacion getTipoDepreciacion() {
        return tipoDepreciacion;
    }

    public void setTipoDepreciacion(TipoDepreciacion tipoDepreciacion) {
        this.tipoDepreciacion = tipoDepreciacion;
    }

    public GrupoActivo getGrupoActivo() {
        return grupoActivo;
    }

    public void setGrupoActivo(GrupoActivo grupoActivo) {
        this.grupoActivo = grupoActivo;
    }

    public ClasificacionActivo getClasificacionActivo() {
        return clasificacionActivo;
    }

    public void setClasificacionActivo(ClasificacionActivo clasificacionActivo) {
        this.clasificacionActivo = clasificacionActivo;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public CuentaContable getCuentaContable() {
        return cuentaContable;
    }

    public void setCuentaContable(CuentaContable cuentaContable) {
        this.cuentaContable = cuentaContable;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public BigDecimal getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(BigDecimal valorInicial) {
        this.valorInicial = valorInicial;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public EstadoActivo getEstado() {
        return estado;
    }

    public void setEstado(EstadoActivo estado) {
        this.estado = estado;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getNombreI18n() {
        return nombreI18n;
    }

    public void setNombreI18n(String nombreI18n) {
        this.nombreI18n = nombreI18n;
    }
}
