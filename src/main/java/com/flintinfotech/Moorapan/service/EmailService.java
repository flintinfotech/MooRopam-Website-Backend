package com.flintinfotech.Moorapan.service;


public interface EmailService {

    void sendPaymentEmail(String customerName, String customerEmail, double amount, String paymentId, String productName);

}