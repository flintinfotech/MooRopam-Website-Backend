package com.flintinfotech.Moorapan.dto;

import java.math.BigDecimal;

public class CreateRazorpayOrderRequest {
    private BigDecimal amount; // In rupees
    private String currency = "INR";
    private String receipt;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }
}