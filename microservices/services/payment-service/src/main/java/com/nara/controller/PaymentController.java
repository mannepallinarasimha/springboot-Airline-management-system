package com.nara.controller;

import com.nara.dto.PaymentRequestDTO;
import com.nara.dto.PaymentResponseDTO;
import com.nara.enums.PaymentStatus;
import com.nara.service.PaymentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    /**
     * Initiate a new payment
     * POST /api/v1/payments/initiate
     */
    @PostMapping("/initiate")
    public ResponseEntity<?> initiatePayment(@Valid @RequestBody PaymentRequestDTO paymentRequest) {
        log.info("API Request: Initiate payment for booking {}", paymentRequest.getBookingId());

        try {
            PaymentResponseDTO response = paymentService.initiatePayment(paymentRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Payment initiation failed: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Payment processing failed");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Get payment details by payment ID
     * GET /api/v1/payments/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentById(@PathVariable Long id) {
        log.info("API Request: Get payment with ID {}", id);

        try {
            PaymentResponseDTO response = paymentService.getPaymentById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Payment retrieval failed: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Payment not found");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Get payment by transaction ID
     * GET /api/v1/payments/transaction/{transactionId}
     */
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<?> getPaymentByTransactionId(@PathVariable String transactionId) {
        log.info("API Request: Get payment with transaction ID {}", transactionId);

        try {
            PaymentResponseDTO response = paymentService.getPaymentByTransactionId(transactionId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Payment retrieval failed: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Payment not found");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Get all payments for a booking
     * GET /api/v1/payments/booking/{bookingId}
     */
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<?> getPaymentsByBookingId(@PathVariable Long bookingId) {
        log.info("API Request: Get payments for booking {}", bookingId);

        try {
            List<PaymentResponseDTO> response = paymentService.getPaymentsByBookingId(bookingId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Payment retrieval failed: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error retrieving payments");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Get payment history for a user
     * GET /api/v1/payments/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserPaymentHistory(@PathVariable Long userId) {
        log.info("API Request: Get payment history for user {}", userId);

        try {
            List<PaymentResponseDTO> response = paymentService.getUserPaymentHistory(userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Payment history retrieval failed: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error retrieving payment history");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Process refund for a payment
     * POST /api/v1/payments/{id}/refund
     */
    @PostMapping("/{id}/refund")
    public ResponseEntity<?> refundPayment(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) {
        log.info("API Request: Refund payment {} with amount {}", id, amount);

        try {
            PaymentResponseDTO response = paymentService.processRefund(id, amount);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Refund processing failed: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Refund processing failed");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Get payments by status (Admin endpoint)
     * GET /api/v1/payments/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getPaymentsByStatus(@PathVariable PaymentStatus status) {
        log.info("API Request: Get payments with status {}", status);

        try {
            List<PaymentResponseDTO> response = paymentService.getPaymentsByStatus(status);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Payment retrieval failed: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error retrieving payments");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Health check endpoint
     * GET /api/v1/payments/health
     */
    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        log.info("API Request: Payment service health check");
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Payment Service");
        response.put("timestamp", java.time.LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }
}
