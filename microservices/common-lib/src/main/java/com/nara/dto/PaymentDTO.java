package com.nara.dto;

import com.nara.enums.PaymentStatus;
import com.nara.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO representing a payment transaction for transfer between services
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {

    private Long paymentId;
    private Long bookingId;
    private Long userId;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    private PaymentMethod paymentMethod;
    private String transactionId;
    private String maskedCardNumber;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}
