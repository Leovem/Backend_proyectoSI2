package com.activofijo.backend.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PagoSuscripcionDTO {

    private Long id;

    @NotNull
    private Long suscripcionId;

    @PastOrPresent
    private LocalDate fechaPago;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal monto;

    @NotBlank
    @Pattern(regexp = "Tarjeta|Transferencia|PayPal|Efectivo")
    private String metodoPago;

    @NotBlank
    @Pattern(regexp = "Completado|Pendiente|Fallido")
    private String estadoPago;

    private String observaciones;

    // Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getSuscripcionId() {
        return suscripcionId;
    }
    
    public void setSuscripcionId(Long suscripcionId) {
        this.suscripcionId = suscripcionId;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }
    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }
    public BigDecimal getMonto() {
        return monto;
    }
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    public String getMetodoPago() {
        return metodoPago;
    }
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
    public String getEstadoPago() {
        return estadoPago;
    }
    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
