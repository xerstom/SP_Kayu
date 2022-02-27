package com.boulec.kayu.repositories;

import com.boulec.kayu.models.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, String> {
    List<Basket> findAllByIdMail(String mail);
    Optional<Basket> findByIdMailAndIdBarCode(String mail, long barCode);
}
