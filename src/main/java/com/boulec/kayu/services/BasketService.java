package com.boulec.kayu.services;

import com.boulec.kayu.dtos.BasketDto;
import com.boulec.kayu.models.Basket;
import com.boulec.kayu.repositories.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BasketService {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ScoreCompute scoreCompute;

    public Optional<BasketDto> get(String mail) {
        List<Basket> b = basketRepository.findAllByIdMail(mail);
        if (b.size() == 0) {
            return Optional.empty();
        }


        BasketDto basket = new BasketDto();
        for (long id : b.stream().map(e -> e.getId().getBarCode()).collect(Collectors.toList())) {
            // There can't be an invalid id in the basket
            // If there is one, default to null
            basket.getProducts().add(productService.getOne(id).orElse(null));
        }

        basket.setNutritionScore((int) scoreCompute.computeAverageNutritionScore(basket.getProducts()));

        List<String> classAndColor = scoreCompute.computeClassAndColor(basket.getNutritionScore());
        basket.setClasse(classAndColor.get(0));
        basket.setColor(classAndColor.get(1));

        return Optional.of(basket);
    }

    public boolean add(long id, String mail) {

        if (productService.getOrFetch(id) == null) {
            return false;
        }

        Basket b = new Basket(mail, id);

        try {
            basketRepository.save(b);
            return true;
        } catch (DataAccessException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean delete(long id, String mail) {
        try {
            Optional<Basket> b = basketRepository.findByIdMailAndIdBarCode(mail, id);
            if (b.isEmpty()) {
                return false;
            }

            basketRepository.delete(b.get());
            return true;
        } catch (DataAccessException e) {
            System.out.println(e);
            return false;
        }
    }
}