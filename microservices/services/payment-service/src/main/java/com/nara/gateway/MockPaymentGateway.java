package com.nara.gateway;

import com.nara.dto.PaymentRequestDTO;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

/**
 * Mock Payment Gateway Implementation for Testing
 * 
 * Test Cards:
 * - 4111111111111111 (VISA - Always Success)
 * - 4000000000000002 (VISA - Always Fails)
 * - 5555555555554444 (MasterCard - 70% Success Rate)
 * - 378282246310005 (AMEX - Random Success/Failure)
 */
@Service
public class MockPaymentGateway implements PaymentGateway {

    private static final Random random = new Random();
    
    // Test card patterns
    private static final String TEST_CARD_SUCCESS = "4111111111111111";
    private static final String TEST_CARD_FAILURE = "4000000000000002";
    private static final String TEST_CARD_RANDOM = "5555555555554444";
    private static final String TEST_CARD_AMEX = "378282246310005";

    @Override
    public PaymentGatewayResponse processPayment(PaymentRequestDTO paymentRequest) {
        // Validate card details
        if (!validateCard(paymentRequest.getCardNumber(), paymentRequest.getExpiryDate(), paymentRequest.getCvv())) {
            return PaymentGatewayResponse.builder()
                    .success(false)
                    .errorMessage("Invalid card details provided")
                    .message("Card validation failed")
                    .build();
        }

        String cardNumber = paymentRequest.getCardNumber();
        boolean transactionSuccess = determineTransactionSuccess(cardNumber);

        String transactionId = UUID.randomUUID().toString();
        String maskedCard = maskCardNumber(cardNumber);

        if (transactionSuccess) {
            return PaymentGatewayResponse.builder()
                    .success(true)
                    .transactionId(transactionId)
                    .message("Payment processed successfully")
                    .authorizationCode(generateAuthCode())
                    .processedAmount(paymentRequest.getAmount())
                    .maskedCardNumber(maskedCard)
                    .build();
        } else {
            return PaymentGatewayResponse.builder()
                    .success(false)
                    .transactionId(transactionId)
                    .errorMessage("Payment declined by gateway (Mock)")
                    .message("Transaction failed")
                    .maskedCardNumber(maskedCard)
                    .build();
        }
    }

    @Override
    public PaymentGatewayResponse refundPayment(String transactionId, BigDecimal amount) {
        // Simulate refund processing
        boolean refundSuccess = random.nextBoolean() || random.nextDouble() > 0.3;

        if (refundSuccess) {
            return PaymentGatewayResponse.builder()
                    .success(true)
                    .transactionId(transactionId)
                    .message("Refund processed successfully")
                    .authorizationCode(generateAuthCode())
                    .processedAmount(amount)
                    .build();
        } else {
            return PaymentGatewayResponse.builder()
                    .success(false)
                    .transactionId(transactionId)
                    .errorMessage("Refund failed - Transaction not found or already refunded")
                    .message("Refund processing failed")
                    .build();
        }
    }

    @Override
    public boolean validateCard(String cardNumber, String expiryDate, String cvv) {
        // Validate card number length
        if (cardNumber == null || cardNumber.length() < 13 || cardNumber.length() > 19) {
            return false;
        }

        // Validate expiry date format (MM/YY)
        if (expiryDate == null || !expiryDate.matches("^(0[1-9]|1[0-2])/\\d{2}$")) {
            return false;
        }

        // Validate CVV
        if (cvv == null || !cvv.matches("^\\d{3,4}$")) {
            return false;
        }

        // Check expiry date is in future
        return isExpiryDateValid(expiryDate);
    }

    @Override
    public String getGatewayName() {
        return "Mock Payment Gateway";
    }

    /**
     * Determine if transaction should succeed based on card number
     */
    private boolean determineTransactionSuccess(String cardNumber) {
        if (TEST_CARD_SUCCESS.equals(cardNumber)) {
            return true;
        }
        if (TEST_CARD_FAILURE.equals(cardNumber)) {
            return false;
        }
        if (TEST_CARD_RANDOM.equals(cardNumber)) {
            // 70% success rate
            return random.nextDouble() < 0.7;
        }
        if (TEST_CARD_AMEX.equals(cardNumber)) {
            // Random success/failure
            return random.nextBoolean();
        }
        // Default: 80% success rate for unknown cards
        return random.nextDouble() < 0.8;
    }

    /**
     * Mask card number showing only last 4 digits
     */
    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        String lastFour = cardNumber.substring(cardNumber.length() - 4);
        return "**** **** **** " + lastFour;
    }

    /**
     * Generate authorization code
     */
    private String generateAuthCode() {
        return String.format("AUTH%06d", random.nextInt(1000000));
    }

    /**
     * Validate card expiry date
     */
    private boolean isExpiryDateValid(String expiryDate) {
        try {
            String[] parts = expiryDate.split("/");
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1]);

            // Parse year (assuming YY format for current century)
            if (year < 100) {
                year += 2000;
            }

            int currentYear = java.time.Year.now().getValue();
            int currentMonth = java.time.Month.now().getValue();

            if (year < currentYear) {
                return false;
            }
            if (year == currentYear && month < currentMonth) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
