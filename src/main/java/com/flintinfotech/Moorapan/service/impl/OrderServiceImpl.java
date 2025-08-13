package com.flintinfotech.Moorapan.service.impl;

import com.flintinfotech.Moorapan.dto.ItemDto;
import com.flintinfotech.Moorapan.dto.PaymentRequestDto;
import com.flintinfotech.Moorapan.entity.*;
import com.flintinfotech.Moorapan.repository.*;
import com.flintinfotech.Moorapan.service.EmailService;
import com.flintinfotech.Moorapan.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private EmailService emailService;

    @Value("${razorpay.secret}")
    private String razorpaySecret;

    // Repositories - assume these are JPA repositories injected here
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;


    public OrderServiceImpl(ProductRepository productRepository, UserRepository userRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, PaymentRepository paymentRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public void processPaymentAndCreateOrder(PaymentRequestDto paymentRequest) throws Exception {
        //Mock signature verification for testing (uncomment and implement real check if needed)


        if (!verifyRazorpaySignature(paymentRequest.getRazorpayOrderId(),
                paymentRequest.getRazorpayPaymentId(),
                paymentRequest.getRazorpaySignature())) {
            throw new Exception("Invalid payment signature");
        }

        // System.out.println("✅ Razorpay signature mock verified");


        // Check stock availability
        for (ItemDto item : paymentRequest.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductId()));
            if (product.getStockQuantity() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product " + product.getName());
            }

        }

        // Fetch the user
        User user = userRepository.findById(paymentRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Calculate total amount
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (ItemDto item : paymentRequest.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductId()));
            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        // Create and save the order with totalAmount set
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(paymentRequest.getShippingAddress());
        order.setBillingAddress(paymentRequest.getBillingAddress());
        order.setOrderStatus("Confirmed");
        order.setPaymentStatus("SUCCESS");
        order.setPaymentGateway("RAZORPAY");
        order.setTransactionId(paymentRequest.getRazorpayPaymentId());
        order.setTotalAmount(totalAmount);

        orderRepository.save(order);

        // Create order items, reduce stock, and save
        for (ItemDto item : paymentRequest.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

            orderItemRepository.save(orderItem);

            // Reduce stock quantity and save
            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            productRepository.save(product);
        }

        // Save payment details
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(totalAmount);
        payment.setPaymentMethod("RAZORPAY");
        payment.setPaymentStatus("SUCCESS");
        payment.setTransactionId(paymentRequest.getRazorpayPaymentId());


        paymentRepository.save(payment);

        String productNames = paymentRequest.getItems().stream()
                .map(item -> {
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductId()));
                    return product.getName();
                })
                .collect(Collectors.joining(", ")); // e.g. "T-Shirt, Jeans, Shoes"


        String userFullName = user.getFirstName() + " " + user.getLastName();
        emailService.sendPaymentEmail(
                userFullName,
                user.getEmail(),
                user.getPhoneNumber(),
                user.getShippingAddress(),
                user.getBillingAddress(),
                totalAmount.doubleValue(),
                payment.getTransactionId(),
                productNames


        );


    }


    private boolean verifyRazorpaySignature(String orderId, String paymentId, String razorpaySignature) throws Exception {
        String payload = orderId + "|" + paymentId;

        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(razorpaySecret.getBytes(), "HmacSHA256");
        sha256Hmac.init(secretKey);

        byte[] hash = sha256Hmac.doFinal(payload.getBytes());

        // Convert the generated HMAC to hex format
        String generatedHexSignature = bytesToHex(hash);

        System.out.println("Generated Signature (hex): " + generatedHexSignature);
        System.out.println("Razorpay Signature: " + razorpaySignature);

        return generatedHexSignature.equals(razorpaySignature);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}