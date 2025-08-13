package com.flintinfotech.Moorapan.service;


public interface EmailService {

    void sendPaymentEmail(String customerName, String customerEmail, String phoneNumber
            , String shippingAddress, String billingAddress, double amount, String paymentId, String productName);

}