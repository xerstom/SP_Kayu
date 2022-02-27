package com.boulec.kayu.services;

import com.boulec.kayu.dtos.ProductDto;
import com.boulec.kayu.repositories.NutritionScoreRepository;
import com.boulec.kayu.repositories.RuleRepository;
import com.boulec.kayu.services.off.OFFProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScoreCompute {
    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private NutritionScoreRepository nutritionScoreRepository;

    /**
     * Compute the nutrition score of a Product based on several properties
     *
     * @param p
     * @return The nutrition score
     */
    public int computeNutriScore(OFFProduct p) {
        int total = 0;
        Pageable page = PageRequest.of(0, 1, Sort.by("min_bound").descending());

        total += this.ruleRepository.getRulePoint("energy_100g", p.getEnergy(), page).get(0);
        total += this.ruleRepository.getRulePoint("saturated-fat_100g", p.getSaturatedFat(), page).get(0);
        total += this.ruleRepository.getRulePoint("sugars_100g", p.getSugars(), page).get(0);
        total += this.ruleRepository.getRulePoint("salt_100g", p.getSalt(), page).get(0);
        total -= this.ruleRepository.getRulePoint("fiber_100g", p.getFiber(), page).get(0);
        total -= this.ruleRepository.getRulePoint("proteins_100g", p.getProteins(), page).get(0);
        return total;
    }

    /**
     * Compute the class and color of a Product based on its nutrition score
     *
     * @param nutriScore
     * @return A Tuple with the class and the color (Classe, Color)
     */
    public List<String> computeClassAndColor(int nutriScore) {
        return this.nutritionScoreRepository.getClasseAndColor(
                nutriScore, PageRequest.of(0, 1, Sort.by("lower_bound").descending())
        ).get(0);
    }

    /**
     * Compute the average of nutrition scores from a list of Products
     *
     * @param products
     * @return The computed average
     */
    public double computeAverageNutritionScore(List<ProductDto> products) {
        return products.stream()
                .mapToDouble(ProductDto::getNutritionScore)
                .average()
                .orElse(0.0);
    }
}
