package com.activofijo.backend.controller;

import com.activofijo.backend.dto.*;
import com.activofijo.backend.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscripcionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/empresas")
    public ResponseEntity<Long> crearEmpresa(@RequestBody EmpresaCreateDTO request) {
        Long empresaId = subscriptionService.crearEmpresa(request);
        return ResponseEntity.ok(empresaId);
    }

    @PostMapping("/suscripciones")
    public ResponseEntity<SuscripcionDTO> crearSuscripcion(@RequestBody SuscripcionCreateDTO request) {
        SuscripcionDTO suscripcion = subscriptionService.crearSuscripcion(request);
        return ResponseEntity.ok(suscripcion);
    }

    @PostMapping("/pagos")
    public ResponseEntity<PagoSuscripcionDTO> registrarPago(@RequestBody PagoSuscripcionCreateDTO request) {
        PagoSuscripcionDTO pago = subscriptionService.registrarPago(request);
        return ResponseEntity.ok(pago);
    }

    @PostMapping("/usuarios/admin")
    public ResponseEntity<UsuarioDTO> crearUsuarioAdministrador(@RequestBody UsuarioCreateDTO request) {
        UsuarioDTO created = subscriptionService.crearUsuarioAdministrador(request);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/adquirir-plan")
    public ResponseEntity<UsuarioDTO> adquirirPlan(@RequestBody AdquisicionPlanRequestDTO request) {
        UsuarioDTO usuarioAdmin = subscriptionService.adquirirPlan(request);
        return ResponseEntity.ok(usuarioAdmin);
    }

}