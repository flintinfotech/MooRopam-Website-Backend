package com.flintinfotech.Moorapan.service.impl;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class testClass {

    private static final String API_KEY = "re_GTS1aDW8_P3nd5qVFoeDYxv8huhVsJJm6";  // Use your secure API key
    private static final String FROM_EMAIL = "Moorapan <onboarding@resend.dev>";
    private static final String TO_EMAIL = "mooropan@gmail.com";
    private static final String RESEND_API_URL = "https://api.resend.com/emails";

    private static final OkHttpClient client = new OkHttpClient();

    public static void sendPaymentEmail(String customerName, String customerEmail, double amount, String paymentId, String productName) {
        JSONObject body = new JSONObject();
        body.put("from", FROM_EMAIL);
        body.put("to", TO_EMAIL);
        body.put("subject", "🧾 New Payment Received");

        String htmlContent = "<h2>🧾 Payment Details</h2>" +
                "<p><strong>Name:</strong> " + customerName + "</p>" +
                "<p><strong>Email:</strong> " + customerEmail + "</p>" +
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
                System.out.println("🔄 HTTP Response Code: " + response.code());
                System.out.println("🔍 Response: " + response.body().string());
            } else {
                System.err.println("❌ Failed to send email. HTTP Code: " + response.code());
                System.err.println("🔍 Error Response: " + response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Main method for testing
    public static void main(String[] args) {
        String customerName = "Gitesh Darbastwar";
        String customerEmail = "giteshdarbastwar2812@gmail.com";
        double amount = 1200.00;
        String paymentId = "PAY12345678";
        String productName = "Moorapan Premium Service";

        sendPaymentEmail(customerName, customerEmail, amount, paymentId, productName);
    }
}