package com.activofijo.backend.dto;



class VehiculoCreateDTO {
    private Long activoId;
    private Long empresaId;
    private String placa;
    private String motor;
    private String chasis;
    // Getters y setters...

    public Long getActivoId() { return activoId; }
    public void setActivoId(Long activoId) { this.activoId = activoId; }

    public Long getEmpresaId() { return empresaId; }
    public void setEmpresa(Long empresaId) { this.empresaId = empresaId; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getMotor() { return motor; }
    public void setMotor(String motor) { this.motor = motor; }

    public String getChasis() { return chasis; }
    public void setChasis(String chasis) { this.chasis = chasis; }
}
