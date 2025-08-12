package com.flintinfotech.Moorapan.repository;

import com.flintinfotech.Moorapan.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByOrderOrderId(Integer orderId);
}