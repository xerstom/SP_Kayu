package com.boulec.kayu.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
public class BasketId implements Serializable {
    @Column(name = "mail")
    private String mail;
    @Column(name = "bar_code")
    private long barCode;

    public BasketId(String mail, long barCode) {
        this.mail = mail;
        this.barCode = barCode;
    }
}
