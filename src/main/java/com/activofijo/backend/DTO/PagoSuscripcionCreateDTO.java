package com.activofijo.backend.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PagoSuscripcionCreateDTO {

    @NotNull(message = "El ID de la suscripción es obligatorio")
    private Long suscripcionId;

    @PastOrPresent(message = "La fecha de pago no puede ser futura")
    private LocalDate fechaPago;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.00", inclusive = true, message = "El monto debe ser mayor o igual a 0")
    private BigDecimal monto;

    @NotBlank(message = "El método de pago es obligatorio")
    @Pattern(regexp = "Tarjeta|Transferencia|PayPal|Efectivo", message = "Método de pago inválido")
    private String metodoPago;

    @NotBlank(message = "El estado del pago es obligatorio")
    @Pattern(regexp = "Completado|Pendiente|Fallido", message = "Estado de pago inválido")
    private String estadoPago;

    private String observaciones;

    // Getters y Setters

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
