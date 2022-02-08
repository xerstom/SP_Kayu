package com.boulec.kayu.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ProductDto {

    int id;
    String barCode;
    String name;
    int nutritionScore;

    public ProductDto(int id, String barCode, String name, int nutritionScore) {
        this.id = id;
        this.barCode = barCode;
        this.name = name;
        this.nutritionScore = nutritionScore;

    }
    public ProductDto() {}
}