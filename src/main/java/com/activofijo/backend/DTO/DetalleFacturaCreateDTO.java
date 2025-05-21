package com.activofijo.backend.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class DetalleFacturaCreateDTO {

    @NotNull(message = "El ID de la factura es obligatorio")
    private Long facturaId;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @NotNull
    @Min(value = 1, message = "La cantidad mínima es 1")
    private Integer cantidad;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = true, message = "El precio debe ser mayor o igual a 0.00")
    private BigDecimal precioUnitario;

    // Getters y Setters

    public Long getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Long facturaId) {
        this.facturaId = facturaId;
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

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
