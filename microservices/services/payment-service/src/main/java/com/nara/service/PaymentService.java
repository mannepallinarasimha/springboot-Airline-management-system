package com.nara.service;

import com.nara.dto.PaymentRequestDTO;
import com.nara.dto.PaymentResponseDTO;
import com.nara.dto.PaymentDTO;
import com.nara.enums.PaymentStatus;
import com.nara.gateway.PaymentGateway;
import com.nara.gateway.PaymentGatewayResponse;
import com.nara.model.Payment;
import com.nara.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentGateway paymentGateway;

    /**
     * Initiate a payment transaction
     */
    @Transactional
    public PaymentResponseDTO initiatePayment(PaymentRequestDTO paymentRequest) {
        log.info("Initiating payment for booking: {} with amount: {}", 
                paymentRequest.getBookingId(), paymentRequest.getAmount());

        try {
            // Process payment through gateway
            PaymentGatewayResponse gatewayResponse = paymentGateway.processPayment(paymentRequest);

            // Create payment record
            Payment payment = Payment.builder()
                    .bookingId(paymentRequest.getBookingId())
                    .userId(paymentRequest.getUserId())
                    .amount(paymentRequest.getAmount())
                    .currency(paymentRequest.getCurrency())
                    .paymentMethod(paymentRequest.getPaymentMethod())
                    .transactionId(gatewayResponse.getTransactionId())
                    .maskedCardNumber(gatewayResponse.getMaskedCardNumber())
                    .description(paymentRequest.getDescription())
                    .status(gatewayResponse.isSuccess() ? PaymentStatus.SUCCESS : PaymentStatus.FAILED)
                    .failureReason(gatewayResponse.getErrorMessage())
                    .build();

            Payment savedPayment = paymentRepository.save(payment);
            log.info("Payment record created with ID: {} and status: {}", 
                    savedPayment.getId(), savedPayment.getStatus());

            return mapToPaymentResponse(savedPayment, gatewayResponse.getMessage());

        } catch (Exception e) {
            log.error("Error processing payment for booking: {}", paymentRequest.getBookingId(), e);
            throw new RuntimeException("Payment processing failed: " + e.getMessage());
        }
    }

    /**
     * Get payment details by payment ID
     */
    public PaymentResponseDTO getPaymentById(Long paymentId) {
        log.info("Fetching payment details for ID: {}", paymentId);

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + paymentId));

        return mapToPaymentResponse(payment, "Payment retrieved successfully");
    }

    /**
     * Get payment details by transaction ID
     */
    public PaymentResponseDTO getPaymentByTransactionId(String transactionId) {
        log.info("Fetching payment details for transaction ID: {}", transactionId);

        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Payment not found with transaction ID: " + transactionId));

        return mapToPaymentResponse(payment, "Payment retrieved successfully");
    }

    /**
     * Get all payments for a booking
     */
    public List<PaymentResponseDTO> getPaymentsByBookingId(Long bookingId) {
        log.info("Fetching all payments for booking: {}", bookingId);

        List<Payment> payments = paymentRepository.findByBookingId(bookingId);
        return payments.stream()
                .map(p -> mapToPaymentResponse(p, "Payment retrieved successfully"))
                .collect(Collectors.toList());
    }

    /**
     * Get payment history for a user
     */
    public List<PaymentResponseDTO> getUserPaymentHistory(Long userId) {
        log.info("Fetching payment history for user: {}", userId);

        List<Payment> payments = paymentRepository.findUserPaymentHistory(userId);
        return payments.stream()
                .map(p -> mapToPaymentResponse(p, "Payment retrieved successfully"))
                .collect(Collectors.toList());
    }

    /**
     * Process refund for a payment
     */
    @Transactional
    public PaymentResponseDTO processRefund(Long paymentId, BigDecimal refundAmount) {
        log.info("Processing refund for payment ID: {} with amount: {}", paymentId, refundAmount);

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + paymentId));

        // Validate refund eligibility
        if (!PaymentStatus.SUCCESS.equals(payment.getStatus())) {
            throw new RuntimeException("Only successful payments can be refunded");
        }

        if (refundAmount.compareTo(payment.getAmount()) > 0) {
            throw new RuntimeException("Refund amount cannot exceed original payment amount");
        }

        // Process refund through gateway
        PaymentGatewayResponse refundResponse = paymentGateway.refundPayment(
                payment.getTransactionId(), 
                refundAmount);

        // Update payment status
        if (refundResponse.isSuccess()) {
            payment.setStatus(PaymentStatus.REFUNDED);
            log.info("Refund successful for payment ID: {}", paymentId);
        } else {
            log.error("Refund failed for payment ID: {}", paymentId);
            throw new RuntimeException("Refund processing failed: " + refundResponse.getErrorMessage());
        }

        Payment updatedPayment = paymentRepository.save(payment);
        return mapToPaymentResponse(updatedPayment, "Refund processed successfully");
    }

    /**
     * Get all payments with specific status
     */
    public List<PaymentResponseDTO> getPaymentsByStatus(PaymentStatus status) {
        log.info("Fetching payments with status: {}", status);

        List<Payment> payments = paymentRepository.findByStatus(status);
        return payments.stream()
                .map(p -> mapToPaymentResponse(p, "Payment retrieved successfully"))
                .collect(Collectors.toList());
    }

    /**
     * Map Payment entity to PaymentResponseDTO
     */
    private PaymentResponseDTO mapToPaymentResponse(Payment payment, String message) {
        return PaymentResponseDTO.builder()
                .paymentId(payment.getId())
                .bookingId(payment.getBookingId())
                .userId(payment.getUserId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .status(payment.getStatus())
                .paymentMethod(payment.getPaymentMethod())
                .transactionId(payment.getTransactionId())
                .maskedCardNumber(payment.getMaskedCardNumber())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .message(message)
                .errorMessage(payment.getFailureReason())
                .build();
    }

    /**
     * Map Payment entity to PaymentDTO
     */
    public PaymentDTO mapToPaymentDTO(Payment payment) {
        return PaymentDTO.builder()
                .paymentId(payment.getId())
                .bookingId(payment.getBookingId())
                .userId(payment.getUserId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .status(payment.getStatus())
                .paymentMethod(payment.getPaymentMethod())
                .transactionId(payment.getTransactionId())
                .maskedCardNumber(payment.getMaskedCardNumber())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}
