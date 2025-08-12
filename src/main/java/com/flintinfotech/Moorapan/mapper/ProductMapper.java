package com.flintinfotech.Moorapan.mapper;

import com.flintinfotech.Moorapan.dto.ProductDTO;
import com.flintinfotech.Moorapan.entity.Product;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setImageUrl(product.getImageUrl());
        dto.setCategory(product.getCategory());
//        dto.setCreatedAt(product.getCreatedAt());
//        dto.setUpdatedAt(product.getUpdatedAt());
        return dto;
    }

    public static Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setImageUrl(dto.getImageUrl());
        product.setCategory(dto.getCategory());
        return product;
    }
}