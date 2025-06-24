package com.activofijo.backend.dto;


class VehiculoListDTO {
    private Long id;
    private String activoNombre;
    private String empresaNombre;
    private String placa;
    private String motor;
    private String chasis;
    // Getters y setters...

        public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getActivoNombre() { return activoNombre; }
    public void setActivoNombre(String activoNombre) { this.activoNombre = activoNombre; }

    public String getEmpresaNombre() { return empresaNombre; }
    public void setEmpresaNombre(String empresaNombre) { this.empresaNombre = empresaNombre; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getMotor() { return motor; }
    public void setMotor(String motor) { this.motor = motor; }

    public String getChasis() { return chasis; }
    public void setChasis(String chasis) { this.chasis = chasis; }
}
