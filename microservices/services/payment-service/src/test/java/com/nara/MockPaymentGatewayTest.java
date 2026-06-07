package com.nara.gateway;

import com.nara.dto.PaymentRequestDTO;
import com.nara.enums.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class MockPaymentGatewayTest {

    private MockPaymentGateway gateway;
    private PaymentRequestDTO paymentRequest;

    @BeforeEach
    public void setUp() {
        gateway = new MockPaymentGateway();
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
                .build();
    }

    @Test
    public void testProcessPayment_WithSuccessCard() {
        PaymentGatewayResponse response = gateway.processPayment(paymentRequest);

        assertTrue(response.isSuccess());
        assertNotNull(response.getTransactionId());
        assertNotNull(response.getAuthorizationCode());
        assertEquals("**** **** **** 1111", response.getMaskedCardNumber());
    }

    @Test
    public void testProcessPayment_WithFailureCard() {
        paymentRequest.setCardNumber("4000000000000002");
        PaymentGatewayResponse response = gateway.processPayment(paymentRequest);

        assertFalse(response.isSuccess());
        assertNotNull(response.getErrorMessage());
    }

    @Test
    public void testValidateCard_Success() {
        assertTrue(gateway.validateCard("4111111111111111", "12/25", "123"));
    }

    @Test
    public void testValidateCard_InvalidExpiry() {
        assertFalse(gateway.validateCard("4111111111111111", "01/20", "123"));
    }

    @Test
    public void testValidateCard_InvalidCVV() {
        assertFalse(gateway.validateCard("4111111111111111", "12/25", "12"));
    }

    @Test
    public void testGetGatewayName() {
        assertEquals("Mock Payment Gateway", gateway.getGatewayName());
    }
}
