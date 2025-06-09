package com.activofijo.backend.dto;

import jakarta.validation.constraints.*;

public class SuscripcionCreateInternaDTO {

    @NotNull(message = "El ID del plan es obligatorio")
    private Long planId;

    @NotBlank(message = "El tipo de periodo es obligatorio")
    @Pattern(regexp = "Mensual|Semestral|Anual", message = "El tipo de periodo debe ser Mensual, Semestral o Anual")
    private String tipoPeriodo;

    // Getters y setters
    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getTipoPeriodo() {
        return tipoPeriodo;
    }

    public void setTipoPeriodo(String tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }
}
