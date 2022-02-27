package com.boulec.kayu;

import com.boulec.kayu.dtos.ProductDto;
import com.boulec.kayu.services.ScoreCompute;
import com.boulec.kayu.services.off.OFFProduct;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ScoreComputeTest {

    @Autowired
    private ScoreCompute scoreCompute;

    @Test
    public void testNutritionScore() throws Exception {
        OFFProduct p = new OFFProduct(7622210449283L, "BISCUITS FOURRÉS (35%) PARFUM CHOCOLAT", 467, 5.6F, 32, 0.49F, 4, 6.3F);

        Assertions.assertEquals(scoreCompute.computeNutriScore(p), 6);
    }

    @Test
    public void testClasseAndColor() throws Exception {
        List<String> classAndColor = scoreCompute.computeClassAndColor(6);

        Assertions.assertEquals(classAndColor.get(0), "Mangeable");
        Assertions.assertEquals(classAndColor.get(1), "yellow");
    }

    @Test
    public void testAverageNutritionScore() throws Exception {

        ProductDto p1 = new ProductDto(3274080005003L, "Acqua minerale", 0, "Bon", "light green");
        ProductDto p2 = new ProductDto(3017620425035L, "Pâte à tartiner aux noisettes", 23, "Degueu", "red");
        ProductDto p3 = new ProductDto(7622210449283L, "BISCUITS FOURRÉS (35%) PARFUM CHOCOLAT", 10, "Mangeable", "yellow");
        Assertions.assertEquals(scoreCompute.computeAverageNutritionScore(List.of(p1, p2, p3)), 11);
    }

}
