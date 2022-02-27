package com.boulec.kayu.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Data
@Entity(name = "basket")
@Table(name = "basket")
@NoArgsConstructor
@AllArgsConstructor
public class Basket {
    @EmbeddedId
    BasketId id;

    public Basket(String mail, long barCode) {
        this.id = new BasketId(mail, barCode);
    }
}
