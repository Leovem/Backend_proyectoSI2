package com.activofijo.backend.DTO;

public class JwtResponse {
    private String token;
    private String nombre_completo;
    private String role;

    public JwtResponse(String token, String nombre_completo, String role) {
        this.token = token;
        this.nombre_completo = nombre_completo;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return nombre_completo;
    }

    public String getRole() {
        return role;
    }
}
