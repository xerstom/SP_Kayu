package com.boulec.kayu.controllers;

import com.boulec.kayu.dtos.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.boulec.kayu.services.MainService;

@RestController
public class MainController {

        @Autowired
        MainService mainService;

        @GetMapping("/")
        String all() {
            return "coucou";
        }

        @GetMapping("/product/{id}")
        ProductDto getProduct(@PathVariable String id) {
            return mainService.getProductService(id);
        }
}
