package com.boulec.kayu.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {

    long barCode;
    String name;
    int nutritionScore;
    String classe;
    String color;
}