package com.activofijo.backend.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class DetalleOrdenCompraCreateDTO {

    @NotNull(message = "El ID de la orden es obligatorio")
    private Long ordenId;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @NotNull @Min(value = 1, message = "La cantidad mínima es 1")
    private Integer cantidad;

    @NotNull @DecimalMin(value = "0.00", message = "El precio debe ser mayor o igual a 0.00")
    private BigDecimal precioEstimado;

    // Getters y Setters
    public Long getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(Long ordenId) {
        this.ordenId = ordenId;
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
}
