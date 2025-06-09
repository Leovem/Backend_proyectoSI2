package com.activofijo.backend.dto;

public class AdquisicionPlanRequestDTO {

    private EmpresaCreateDTO empresa;
    private SuscripcionCreateInternaDTO suscripcion;
    private PagoSuscripcionCreateDTO pago;
    private UsuarioCreateDTO usuarioAdmin;

    // Getters y setters

    public EmpresaCreateDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaCreateDTO empresa) {
        this.empresa = empresa;
    }

    public SuscripcionCreateInternaDTO getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(SuscripcionCreateInternaDTO suscripcion) {
        this.suscripcion = suscripcion;
    }

    public PagoSuscripcionCreateDTO getPago() {
        return pago;
    }

    public void setPago(PagoSuscripcionCreateDTO pago) {
        this.pago = pago;
    }

    public UsuarioCreateDTO getUsuarioAdmin() {
        return usuarioAdmin;
    }

    public void setUsuarioAdmin(UsuarioCreateDTO usuarioAdmin) {
        this.usuarioAdmin = usuarioAdmin;
    }
}
