package com.nara.gateway;

import com.nara.dto.PaymentRequestDTO;

/**
 * Interface for payment gateway implementations
 * Allows for multiple gateway implementations (mock, Stripe, Razorpay, etc.)
 */
public interface PaymentGateway {

    /**
     * Process a payment transaction
     * @param paymentRequest The payment request details
     * @return PaymentGatewayResponse with transaction details
     */
    PaymentGatewayResponse processPayment(PaymentRequestDTO paymentRequest);

    /**
     * Refund a previously processed payment
     * @param transactionId The transaction ID to refund
     * @param amount The amount to refund
     * @return PaymentGatewayResponse with refund details
     */
    PaymentGatewayResponse refundPayment(String transactionId, java.math.BigDecimal amount);

    /**
     * Validate card details without processing payment
     * @param cardNumber The card number
     * @param expiryDate The expiry date
     * @param cvv The CVV
     * @return true if card is valid, false otherwise
     */
    boolean validateCard(String cardNumber, String expiryDate, String cvv);

    /**
     * Get gateway name/identifier
     * @return Gateway name
     */
    String getGatewayName();
}
