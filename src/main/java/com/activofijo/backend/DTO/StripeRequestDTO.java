package com.activofijo.backend.dto;

public class StripeRequestDTO {
    private String nombrePlan;
    private Long monto; // En centavos (ej: 9900 = $99.00)

    public String getNombrePlan() {
        return nombrePlan;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    public Long getMonto() {
        return monto;
    }

    public void setMonto(Long monto) {
        this.monto = monto;
    }
}
