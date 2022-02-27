package com.boulec.kayu.controllers;

import com.boulec.kayu.dtos.BasketDto;
import com.boulec.kayu.services.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/basket")
public class BasketController {

    @Autowired
    private BasketService basketService;

    @GetMapping
    ResponseEntity<BasketDto> getBasket(@RequestParam String mail) {
        Optional<BasketDto> basket = basketService.get(mail);

        if (basket.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(basket.get());
    }

    @PostMapping("/{id}")
    ResponseEntity<?> addToBasket(@PathVariable long id, @RequestParam String mail) {
        if (basketService.add(id, mail)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteFromBasket(@PathVariable long id, @RequestParam String mail) {
        if (basketService.delete(id, mail)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
