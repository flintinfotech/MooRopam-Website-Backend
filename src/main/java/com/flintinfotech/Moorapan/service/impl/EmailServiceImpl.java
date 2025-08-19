package com.flintinfotech.Moorapan.service.impl;

import com.flintinfotech.Moorapan.service.EmailService;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${Moorapan.resendAPIKey}")
    private String API_KEY;

    private static final String FROM_EMAIL = "Moorapan <onboarding@resend.dev>";
    private static final String TO_EMAIL = "mooropan@gmail.com";
    private static final String RESEND_API_URL = "https://api.resend.com/emails";

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public void sendPaymentEmail(String customerName, String customerEmail, String phoneNumber, String shippingAddress, String billingAddress, double amount, String paymentId, String productName) {
        JSONObject body = new JSONObject();
        body.put("from", FROM_EMAIL);
        body.put("to", TO_EMAIL);
        body.put("subject", "🧾 New Payment Received");

        String htmlContent = "<h2>🧾 Payment Details</h2>" +
                "<p><strong>Name:</strong> " + customerName + "</p>" +
                "<p><strong>Email:</strong> " + customerEmail + "</p>" +
                "<p><strong>Phone:</strong> " + phoneNumber + "</p>" +
                "<p><strong>Shipping address:</strong> " + shippingAddress + "</p>" +
                "<p><strong>Billing address:</strong> " + billingAddress + "</p>" +
                "<p><strong>Product:</strong> " + productName + "</p>" +
                "<p><strong>Amount:</strong> ₹" + amount + "</p>" +
                "<p><strong>Payment ID:</strong> " + paymentId + "</p>";

        body.put("html", htmlContent);

        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json"),
                body.toString()
        );

        Request request = new Request.Builder()
                .url(RESEND_API_URL)
                .post(requestBody)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("✅ Email sent successfully.");
            } else {
                System.err.println("❌ Failed to send email: " + response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}