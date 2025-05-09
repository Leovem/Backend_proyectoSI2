package com.activofijo.backend.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_orden_compra")
public class DetalleOrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orden_id", nullable = false)
    private OrdenCompra ordenCompra;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioEstimado;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    // Constructor vacío
    public DetalleOrdenCompra() {}

    // Constructor con campos
    public DetalleOrdenCompra(OrdenCompra ordenCompra, String descripcion, Integer cantidad,
                               BigDecimal precioEstimado) {
        this.ordenCompra = ordenCompra;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioEstimado = precioEstimado;
        this.subtotal = calcularSubtotal(); // Se calcula el subtotal
    }

    // Método para calcular el subtotal (cantidad * precioEstimado)
    public BigDecimal calcularSubtotal() {
        return precioEstimado.multiply(BigDecimal.valueOf(cantidad));
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        this.subtotal = calcularSubtotal(); // Recalcula el subtotal
    }

    public BigDecimal getPrecioEstimado() {
        return precioEstimado;
    }

    public void setPrecioEstimado(BigDecimal precioEstimado) {
        this.precioEstimado = precioEstimado;
        this.subtotal = calcularSubtotal(); // Recalcula el subtotal
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
