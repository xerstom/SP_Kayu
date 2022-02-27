package com.boulec.kayu;

import com.boulec.kayu.models.Product;
import com.boulec.kayu.repositories.ProductRepository;
import com.boulec.kayu.services.off.OFFProduct;
import com.boulec.kayu.services.off.OFFRequester;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OFFRequester requester;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void getProductAndNotFound() throws Exception {
        given(requester.request(0L)).willReturn(null);

        mockMvc.perform(
            get("/products/0")
                    .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound());
    }

    @Test
    public void getProductWithoutApi() throws Exception {

        OFFProduct p = new OFFProduct(7622210449283L, "BISCUITS FOURRÉS (35%) PARFUM CHOCOLAT", 467, 5.6F, 32, 0.49F, 4, 6.3F);
        Product pResult = new Product(7622210449283L, "BISCUITS FOURRÉS (35%) PARFUM CHOCOLAT", 6, "Mangeable", "yellow");

        given(requester.request(p.barCode)).willReturn(p);

        mockMvc.perform(
                get("/products/" + p.barCode)
                        .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.barCode").value(pResult.getBarCode()))
            .andExpect(jsonPath("$.name").value(pResult.getName()))
            .andExpect(jsonPath("$.nutritionScore").value(pResult.getNutritionScore()))
            .andExpect(jsonPath("$.classe").value(pResult.getClasse()))
            .andExpect(jsonPath("$.color").value(pResult.getColor()));

        Optional<Product> result = productRepository.findById(p.barCode);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(result.get().getBarCode(), pResult.getBarCode());
        Assertions.assertEquals(result.get().getName(), pResult.getName());
        Assertions.assertEquals(result.get().getNutritionScore(), pResult.getNutritionScore());
        Assertions.assertEquals(result.get().getClasse(), pResult.getClasse());
        Assertions.assertEquals(result.get().getColor(), pResult.getColor());
    }
}
