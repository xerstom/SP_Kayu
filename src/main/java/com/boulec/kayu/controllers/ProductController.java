package com.boulec.kayu.controllers;

import com.boulec.kayu.dtos.ProductDto;
import com.boulec.kayu.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        Optional<ProductDto> product = productService.getOne(id);

        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product.get());
    }
}
