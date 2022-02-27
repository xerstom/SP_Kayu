package com.boulec.kayu.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BasketDto {
    int nutritionScore;
    String classe;
    String color;
    List<ProductDto> products;

    public BasketDto() {
        products = new ArrayList<>();
    }
}
