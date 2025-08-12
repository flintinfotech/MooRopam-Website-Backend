package com.flintinfotech.Moorapan.repository;

import com.flintinfotech.Moorapan.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    // List<Order> findByUserId(Integer userId);
}