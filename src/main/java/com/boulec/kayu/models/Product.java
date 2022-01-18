package com.boulec.kayu.models;

import lombok.Data;

@Data
public class Product {

    private int id;
    private String barCode;
    private String name;
    private int nutritionScore;

}
