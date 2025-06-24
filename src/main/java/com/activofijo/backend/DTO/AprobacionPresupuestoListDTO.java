package com.activofijo.backend.dto;

import java.time.LocalDateTime;

class AprobacionPresupuestoListDTO {
    private Long id;
    private String presupuestoNombre;
    private String usuarioNombre;
    private String estado;
    private LocalDateTime fecha;
    private String comentarios;
    // Getters y setters...
        public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPresupuestoNombre() { return presupuestoNombre; }
    public void setPresupuesto(String presupuestoNombre) { this.presupuestoNombre = presupuestoNombre; }

    public String getUsuarioNombre() { return usuarioNombre; }
    public void setUsuario(String usuarioNombre) { this.usuarioNombre = usuarioNombre; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
}