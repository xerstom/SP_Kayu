package com.boulec.kayu.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity(name = "nutrition_score")
@Table(name = "nutrition_score")
public class NutritionScore {

    @Id
    long id;
    String classe;
    int lower_bound;
    int upper_bound;
    String color;
}
