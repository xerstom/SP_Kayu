package com.boulec.kayu.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="rule")
public class Rule {

    @Id
    private long id;
    private String name;
    private int points;
    private long min_bound;
    private char component;

    public Rule(){}

}
