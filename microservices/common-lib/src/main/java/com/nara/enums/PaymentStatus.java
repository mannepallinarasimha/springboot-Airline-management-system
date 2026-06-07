package com.nara.enums;

/**
 * Represents the status of a payment transaction
 */
public enum PaymentStatus {
    PENDING("Pending - Awaiting processing"),
    SUCCESS("Successful - Transaction completed"),
    FAILED("Failed - Transaction declined"),
    CANCELLED("Cancelled - User or system initiated cancellation"),
    REFUNDED("Refunded - Amount returned to customer");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
