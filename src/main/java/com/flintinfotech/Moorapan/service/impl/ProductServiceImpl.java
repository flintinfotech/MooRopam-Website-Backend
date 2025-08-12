package com.flintinfotech.Moorapan.service.impl;

import com.flintinfotech.Moorapan.dto.ProductDTO;
import com.flintinfotech.Moorapan.entity.Product;
import com.flintinfotech.Moorapan.mapper.ProductMapper;
import com.flintinfotech.Moorapan.repository.ProductRepository;
import com.flintinfotech.Moorapan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = ProductMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
        return ProductMapper.toDTO(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ProductDTO updateProduct(Integer id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));

        if (productDTO.getName() != null) existingProduct.setName(productDTO.getName());
        if (productDTO.getDescription() != null) existingProduct.setDescription(productDTO.getDescription());
        if (productDTO.getPrice() != null) existingProduct.setPrice(productDTO.getPrice());
        if (productDTO.getStockQuantity() != null) existingProduct.setStockQuantity(productDTO.getStockQuantity());
        if (productDTO.getImageUrl() != null) existingProduct.setImageUrl(productDTO.getImageUrl());
        if (productDTO.getCategory() != null) existingProduct.setCategory(productDTO.getCategory());

        Product updated = productRepository.save(existingProduct);
        return ProductMapper.toDTO(updated);
    }

    @Override
    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id " + id);
        }
        productRepository.deleteById(id);
    }

}