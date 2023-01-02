package com.chilleric.franchise_sys.repository.common_enum;

public enum PaymentMethod {
    PAYPAL("Paypal"), CASH("Cash"), BANKING("Banking"), MASTERCARD("Mastercard");

    private String paymentMethod;

    PaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}
