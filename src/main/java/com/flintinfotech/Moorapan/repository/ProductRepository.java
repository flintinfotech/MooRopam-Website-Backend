package com.flintinfotech.Moorapan.repository;

import com.flintinfotech.Moorapan.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository  extends JpaRepository<Product,Integer> {
}