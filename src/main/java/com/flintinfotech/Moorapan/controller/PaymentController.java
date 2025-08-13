package com.flintinfotech.Moorapan.controller;

import com.flintinfotech.Moorapan.dto.CreateRazorpayOrderRequest;
import com.flintinfotech.Moorapan.dto.CreateRazorpayOrderResponse;
import com.flintinfotech.Moorapan.dto.PaymentRequestDto;
import com.flintinfotech.Moorapan.service.OrderService;
import com.flintinfotech.Moorapan.util.Response;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private RazorpayClient razorpayClient;

    @Autowired
    private OrderService orderService;

    // Step 1: Create Razorpay order (no DB save yet)
    @PostMapping("/create-razorpay-order")
    public CreateRazorpayOrderResponse createRazorpayOrder(@RequestBody CreateRazorpayOrderRequest request) throws Exception {
        JSONObject options = new JSONObject();
        int amountInPaise = request.getAmount().multiply(BigDecimal.valueOf(100)).intValue(); // convert rupees to paise
        options.put("amount", amountInPaise);
        options.put("currency", request.getCurrency());
        options.put("receipt", request.getReceipt());

        Order razorpayOrder = razorpayClient.orders.create(options);

        CreateRazorpayOrderResponse response = new CreateRazorpayOrderResponse();
        response.setRazorpayOrderId(razorpayOrder.get("id"));
        response.setCurrency(razorpayOrder.get("currency"));
        response.setAmount(razorpayOrder.get("amount"));
        return response;

    }

    // Step 3 & 4: Verify payment and create order in DB
    @PostMapping("/confirm-payment")
    public ResponseEntity<Response> confirmPayment(@RequestBody PaymentRequestDto paymentRequest) throws Exception {
        try {
            orderService.processPaymentAndCreateOrder(paymentRequest);
            Response response = new Response(paymentRequest, "Success", "Order placed and payment successful");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            Response errorResponse = new Response(e.getMessage(), "ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }


    }
}