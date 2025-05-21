package com.activofijo.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_orden_compra")
public class DetalleOrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orden_id", nullable = false, foreignKey = @ForeignKey(name = "fk_detalle_orden"))
    private OrdenCompra ordenCompra;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @NotNull @Min(1)
    @Column(nullable = false)
    private Integer cantidad;

    @NotNull @DecimalMin("0.00")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioEstimado;

    // subtotal es generado automáticamente, no se debe modificar manualmente
    @Column(nullable = false, precision = 12, scale = 2, insertable = false, updatable = false)
    private BigDecimal subtotal;

    public DetalleOrdenCompra() {}

    public DetalleOrdenCompra(OrdenCompra ordenCompra, String descripcion,
                              Integer cantidad, BigDecimal precioEstimado) {
        this.ordenCompra = ordenCompra;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioEstimado = precioEstimado;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioEstimado() {
        return precioEstimado;
    }

    public void setPrecioEstimado(BigDecimal precioEstimado) {
        this.precioEstimado = precioEstimado;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

}
