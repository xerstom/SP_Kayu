package com.boulec.kayu.dtos;

import lombok.Data;

@Data
public class ProductDto {

    String barCode;
    String name;
    int nutritionScore;

    public ProductDto(String barCode, String name, int nutritionScore) {
        this.barCode = barCode;
        this.name = name;
        this.nutritionScore = nutritionScore;

    }
    public ProductDto() {}
}