package com.flintinfotech.Moorapan.service;


import com.flintinfotech.Moorapan.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO saveProduct(ProductDTO productDTO);

    ProductDTO getProductById(Integer id);

    List<ProductDTO> getAllProducts();

    ProductDTO updateProduct(Integer id, ProductDTO productDTO);

    void deleteProduct(Integer id);
}