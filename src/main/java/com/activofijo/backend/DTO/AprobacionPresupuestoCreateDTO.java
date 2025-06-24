package com.activofijo.backend.dto;


class AprobacionPresupuestoCreateDTO {
    private Long presupuestoId;
    private Long usuarioId;
    private String estado;
    private String comentarios;
    // Getters y setters...
    public Long getPresupuestoId() { return presupuestoId; }
    public void setPresupuesto(Long presupuestoId) { this.presupuestoId = presupuestoId; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
}
