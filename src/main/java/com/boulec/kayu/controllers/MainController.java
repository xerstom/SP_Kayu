package com.boulec.kayu.controllers;

import com.boulec.kayu.dtos.ProductDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

        @GetMapping("/")
        String all() {
            return "coucou";
        }

        @GetMapping("/product/{id}")
        ProductDto getProduct(@PathVariable String id) {
            return new ProductDto(1, "barcode", "name", 5);
        }
}
