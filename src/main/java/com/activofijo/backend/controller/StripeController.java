// StripeController.java
package com.activofijo.backend.controller;

import com.activofijo.backend.dto.AdquisicionPlanRequestDTO;
import com.activofijo.backend.dto.UsuarioDTO;
import com.activofijo.backend.services.SubscriptionService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Value;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
//import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@RestController
@RequestMapping("/api/pagos")
public class StripeController {

    private static final Logger logger = LoggerFactory.getLogger(StripeController.class);
    private final SubscriptionService subscriptionService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${stripe.webhook.key}")
    private String stripeWebhookKey;


    public StripeController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostConstruct
    public void initStripe() {
        Stripe.apiKey = stripeSecretKey;
        logger.info("✅ Stripe API key inicializada");
    }

    // 1. Crear sesión y guardar metadata para el webhook
    @PostMapping("/crear-sesion")
    public ResponseEntity<?> crearSesion(@RequestBody AdquisicionPlanRequestDTO request) throws StripeException {
        logger.info("📦 Recibido crear-sesion con datos: empresa={}, usuarioAdmin={}",
                request.getEmpresa().getNombre(), request.getUsuarioAdmin().getEmail());

        String metadataJson;
        try {
            metadataJson = objectMapper.writeValueAsString(request);
        } catch (Exception e) {
            logger.error("❌ Error serializando metadata del request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error serializando metadata");
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://frontend-proyecto-si-2-285w.vercel.app/pago-exitoso")
                .setCancelUrl("https://frontend-proyecto-si-2-285w.vercel.app/pago-cancelado")

                .setClientReferenceId("adquisicion-plan")
                .putMetadata("adquisicion", metadataJson)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount(request.getPago().getMonto().longValue())
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Plan: "
                                                                        + request.getSuscripcion().getTipoPeriodo())
                                                                .build())
                                                .build())
                                .build())
                .build();

        Session session = Session.create(params);
        logger.info("✅ Sesión Stripe creada: {}", session.getId());
        return ResponseEntity.ok(Map.of("id", session.getId()));
    }

    // 2. Webhook para procesar solo si el pago fue exitoso
    @PostMapping("/webhook")
    public ResponseEntity<String> manejarWebhook(HttpServletRequest request) throws IOException {
        String payload;
        try (Scanner scanner = new Scanner(request.getInputStream()).useDelimiter("\\A")) {
            payload = scanner.hasNext() ? scanner.next() : "";
        }

        String sigHeader = request.getHeader("Stripe-Signature");
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, stripeWebhookKey);
        } catch (SignatureVerificationException e) {
            logger.warn("🚨 Firma del webhook inválida");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Firma inválida");
        }

        if ("checkout.session.completed".equals(event.getType())) {
            logger.info("✅ Evento recibido: checkout.session.completed");

            Session session = null;

            // Intentar deserializar directamente
            var deserializer = event.getDataObjectDeserializer();
            if (deserializer.getObject().isPresent() && deserializer.getObject().get() instanceof Session) {
                session = (Session) deserializer.getObject().get();
            } else {
                // Obtener el ID de la sesión desde el JSON usando Jackson
                try {
                    JsonNode jsonNode = objectMapper.readTree(payload);
                    String sessionId = jsonNode.at("/data/object/id").asText();

                    session = Session.retrieve(sessionId);
                    logger.info("🔁 Sesión recuperada manualmente desde Stripe: {}", sessionId);
                } catch (Exception e) {
                    logger.error("❌ No se pudo recuperar la sesión desde Stripe", e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error recuperando sesión");
                }
            }

            if (session != null && session.getMetadata().containsKey("adquisicion")) {
                try {
                    String jsonMetadata = session.getMetadata().get("adquisicion");
                    AdquisicionPlanRequestDTO requestDTO = objectMapper.readValue(jsonMetadata,
                            AdquisicionPlanRequestDTO.class);

                    UsuarioDTO usuarioCreado = subscriptionService.adquirirPlan(requestDTO);
                    logger.info("🎉 Usuario creado desde webhook: {}", usuarioCreado.getEmail());
                } catch (Exception e) {
                    logger.error("❌ Error procesando adquisición desde webhook", e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en webhook");
                }
            } else {
                logger.warn("⚠️ La sesión no contiene metadata 'adquisicion'");
            }
        }

        return ResponseEntity.ok("Webhook recibido");
    }

}
