package com.nara.dto;

import com.nara.enums.PaymentStatus;
import com.nara.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Request payload for initiating a payment transaction
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDTO {

    @NotNull(message = "Booking ID is required")
    private Long bookingId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "Currency is required")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a valid 3-letter code (e.g., USD, INR)")
    private String currency;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    @NotBlank(message = "Card number is required")
    private String cardNumber;

    @NotBlank(message = "Cardholder name is required")
    private String cardholderName;

    @NotBlank(message = "Expiry date is required")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/\\d{2}$", message = "Expiry date must be in MM/YY format")
    private String expiryDate;

    @NotBlank(message = "CVV is required")
    @Pattern(regexp = "^\\d{3,4}$", message = "CVV must be 3 or 4 digits")
    private String cvv;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
}
