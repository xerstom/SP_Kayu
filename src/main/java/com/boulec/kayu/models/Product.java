package com.boulec.kayu.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity(name = "product")
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    long barCode;
    String name;
    int nutritionScore;
    String classe;
    String color;
}
