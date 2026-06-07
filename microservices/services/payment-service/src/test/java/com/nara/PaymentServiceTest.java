package com.nara.service;

import com.nara.dto.PaymentRequestDTO;
import com.nara.enums.PaymentMethod;
import com.nara.enums.PaymentStatus;
import com.nara.gateway.MockPaymentGateway;
import com.nara.model.Payment;
import com.nara.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private MockPaymentGateway mockPaymentGateway;

    @InjectMocks
    private PaymentService paymentService;

    private PaymentRequestDTO paymentRequest;
    private Payment testPayment;

    @BeforeEach
    public void setUp() {
        paymentRequest = PaymentRequestDTO.builder()
                .bookingId(1L)
                .userId(1L)
                .amount(new BigDecimal("5000.00"))
                .currency("INR")
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .cardNumber("4111111111111111")
                .cardholderName("John Doe")
                .expiryDate("12/25")
                .cvv("123")
                .description("Flight booking payment")
                .build();

        testPayment = Payment.builder()
                .id(1L)
                .bookingId(1L)
                .userId(1L)
                .amount(new BigDecimal("5000.00"))
                .currency("INR")
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .status(PaymentStatus.SUCCESS)
                .transactionId("txn-123456")
                .maskedCardNumber("**** **** **** 1111")
                .build();
    }

    @Test
    public void testGetPaymentById_Success() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(testPayment));

        var response = paymentService.getPaymentById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getPaymentId());
        assertEquals(PaymentStatus.SUCCESS, response.getStatus());
        verify(paymentRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetPaymentById_NotFound() {
        when(paymentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> paymentService.getPaymentById(999L));
    }

    @Test
    public void testGetPaymentsByBookingId() {
        var payments = java.util.List.of(testPayment);
        when(paymentRepository.findByBookingId(1L)).thenReturn(payments);

        var response = paymentService.getPaymentsByBookingId(1L);

        assertNotNull(response);
        assertEquals(1, response.size());
        verify(paymentRepository, times(1)).findByBookingId(1L);
    }
}
