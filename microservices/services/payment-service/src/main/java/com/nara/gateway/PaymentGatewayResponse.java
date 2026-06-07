package com.nara.gateway;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * Response from payment gateway after processing a transaction
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentGatewayResponse {

    private boolean success;
    private String transactionId;
    private String message;
    private String errorMessage;
    private String authorizationCode;
    private BigDecimal processedAmount;
    private String maskedCardNumber;
}
