package com.boulec.kayu.services;

import com.boulec.kayu.dtos.ProductDto;
import com.boulec.kayu.repositories.NutritionScoreRepository;
import com.boulec.kayu.repositories.RuleRepository;
import com.boulec.kayu.services.factory.ProductFactory;
import com.boulec.kayu.services.off.OFFProduct;
import com.boulec.kayu.services.off.OFFRequester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private OFFRequester requester;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private NutritionScoreRepository nutritionScoreRepository;

    public ProductDto getOne(String id) {
        OFFProduct product = this.requester.request(id);
        System.out.println(product);
        if (product == null) {
            return null;
        }
        // requete http
        // OFF PRoduct
        // calcul nutri score
        // creer Product DTO
        // return

        int nutriScore = computeNutriScore(product);
        List<List<String>> classAndColor =  this.nutritionScoreRepository.findTop1ClasseAndColor(
                nutriScore, PageRequest.of(0, 1, Sort.by("lower_bound").descending())
        );
        return ProductFactory.toProductDTO(product, nutriScore,
                classAndColor.get(0).get(0),
                classAndColor.get(0).get(1));
    }

    private int computeNutriScore(OFFProduct p){
        int total = 0;
        Pageable page = PageRequest.of(0, 1, Sort.by("min_bound").descending());

        total += this.ruleRepository.findTop1Points("energy_100g",p.getEnergy(),page).get(0);
        System.out.println(total);
        System.out.println(p.getEnergy());
        total += this.ruleRepository.findTop1Points("saturated-fat_100g",p.getSaturatedFat(),page).get(0);
        System.out.println(total);
        System.out.println(p.getSaturatedFat());
        total += this.ruleRepository.findTop1Points("sugars_100g",p.getSugars(),page).get(0);
        System.out.println(total);
        System.out.println(p.getSugars());
        total += this.ruleRepository.findTop1Points("salt_100g",p.getSalt(),page).get(0);
        System.out.println(total);
        System.out.println(p.getSalt());
        total -= this.ruleRepository.findTop1Points("fiber_100g",p.getFiber(),page).get(0);
        System.out.println(total);
        System.out.println(p.getFiber());
        total -= this.ruleRepository.findTop1Points("proteins_100g",p.getProteins(),page).get(0);
        System.out.println(p.getProteins());
        return total;
    }

}
