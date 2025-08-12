package com.flintinfotech.Moorapan.controller;

import com.flintinfotech.Moorapan.dto.ProductDTO;
import com.flintinfotech.Moorapan.service.ProductService;
import com.flintinfotech.Moorapan.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping("/saveProduct")
    public ResponseEntity<Response> saveProduct(@RequestBody ProductDTO productDTO) {
        try {
            ProductDTO savedProduct = productService.saveProduct(productDTO);
            Response response = new Response(savedProduct, "Success", "Product saved successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response(e.getMessage(), "ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getProductById(@PathVariable("id") Integer id) {
        try {
            ProductDTO productDTO = productService.getProductById(id);
            Response response = new Response(productDTO, "Success", "Product fetched successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response(e.getMessage(), "ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllProducts() {
        try {
            List<ProductDTO> products = productService.getAllProducts();
            Response response = new Response(products, "Success", "Products fetched successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response(e.getMessage(), "ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PatchMapping("/updateProduct/{id}")
    public ResponseEntity<Response> updateProduct(@PathVariable("id") Integer id, @RequestBody ProductDTO productDTO) {
        try {
            ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
            Response response = new Response(updatedProduct, "Success", "Product updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response(e.getMessage(), "ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Response> deleteProduct(@PathVariable("id") Integer id) {
        try {
            productService.deleteProduct(id);
            Response response = new Response(null, "Success", "Product deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response(e.getMessage(), "ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}