package com.activofijo.backend.models;

import com.activofijo.backend.converters.JsonObjectConverter;
import jakarta.json.JsonObject;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
    name = "activos",
    uniqueConstraints = @UniqueConstraint(
        name = "unq_codigo_empresa",
        columnNames = { "codigo", "empresa_id" }
    )
)
public class Activo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}-\\d+$", message = "Código debe tener formato AAA-NNN")
    @Column(name = "codigo", nullable = false, length = 100)
    private String codigo;

    @NotBlank
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "tipo_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_tipo")
    )
    private TipoActivo tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "metodo_depreciacion_id",
        foreignKey = @ForeignKey(name = "fk_metodo_depreciacion")
    )
    private MetodoDepreciacion metodoDepreciacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "tipo_depreciacion_id",
        foreignKey = @ForeignKey(name = "fk_tipo_depreciacion")
    )
    private TipoDepreciacion tipoDepreciacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "grupo_id",
        foreignKey = @ForeignKey(name = "fk_grupo")
    )
    private GrupoActivo grupo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "clasificacion_id",
        foreignKey = @ForeignKey(name = "fk_clasificacion")
    )
    private ClasificacionActivo clasificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "marca_id",
        foreignKey = @ForeignKey(name = "fk_marca")
    )
    private Marca marca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "modelo_id",
        foreignKey = @ForeignKey(name = "fk_modelo")
    )
    private Modelo modelo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "contrato_id",
        foreignKey = @ForeignKey(name = "fk_contrato")
    )
    private TipoContrato contrato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "ubicacion_id",
        foreignKey = @ForeignKey(name = "fk_ubicacion")
    )
    private Ubicacion ubicacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "factura_id",
        foreignKey = @ForeignKey(name = "fk_factura")
    )
    private Factura factura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "cuenta_contable_id",
        foreignKey = @ForeignKey(name = "fk_cuenta_contable")
    )
    private CuentaContable cuentaContable;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "empresa_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_empresa",
            foreignKeyDefinition = "FOREIGN KEY (empresa_id) REFERENCES empresas(id) ON DELETE CASCADE"
        )
    )
    private Empresa empresa;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name = "valor_inicial", nullable = false)
    private BigDecimal valorInicial;

    /** 
     * Se mapea ManyToOne contra la tabla `monedas(codigo)`. 
     * Quitamos @Column y @NotBlank, usamos @NotNull. 
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "moneda",
        referencedColumnName = "codigo",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_moneda")
    )
    private Moneda moneda;

    @NotNull
    @Column(name = "fecha_adquisicion", nullable = false)
    private LocalDate fechaAdquisicion;

    @NotBlank
    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    /**
     * Para JSONB necesitamos un AttributeConverter que convierta JsonObject ↔ String.
     */
    @Column(name = "nombre_i18n", columnDefinition = "JSONB")
    @Convert(converter = JsonObjectConverter.class)
    private JsonObject nombreI18n;

    // -- constructores, getters y setters omitidos para brevedad --



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

    public TipoActivo getTipo() {
        return tipo;
    }

    public void setTipo(TipoActivo tipo) {
        this.tipo = tipo;
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

    public GrupoActivo getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoActivo grupo) {
        this.grupo = grupo;
    }

    public ClasificacionActivo getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(ClasificacionActivo clasificacion) {
        this.clasificacion = clasificacion;
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

    public TipoContrato getContrato() {
        return contrato;
    }

    public void setContrato(TipoContrato contrato) {
        this.contrato = contrato;
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
}