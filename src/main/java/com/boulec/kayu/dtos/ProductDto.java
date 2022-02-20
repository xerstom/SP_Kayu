package com.boulec.kayu.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    String barCode;
    String name;
    int nutritionScore;
    String classe;
    String color;
}