package com.nara.enums;

/**
 * Represents the payment method used for transactions
 */
public enum PaymentMethod {
    CREDIT_CARD("Credit Card"),
    DEBIT_CARD("Debit Card"),
    NET_BANKING("Net Banking"),
    UPI("UPI"),
    WALLET("Digital Wallet"),
    EMI("Equated Monthly Installments");

    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
