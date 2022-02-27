package com.boulec.kayu;

import com.boulec.kayu.models.Basket;
import com.boulec.kayu.repositories.BasketRepository;
import com.boulec.kayu.repositories.ProductRepository;
import com.boulec.kayu.services.off.OFFProduct;
import com.boulec.kayu.services.off.OFFRequester;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners({
        MockitoTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class BasketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private ProductRepository productRepository;

    @MockBean
    private OFFRequester requester;

    @BeforeAll
    @DatabaseSetup("classpath:datasets/reset.xml")
    public static void setUp() { }

    @AfterAll
    @DatabaseSetup("classpath:datasets/reset.xml")
    public static void cleanUp() { }

    @Test
    @DatabaseSetup("classpath:datasets/reset.xml")
    public void addValidProductToBasket() throws Exception {
        OFFProduct p = new OFFProduct(7622210449283L, "BISCUITS FOURRÉS (35%) PARFUM CHOCOLAT", 467, 5.6F, 32, 0.49F, 4, 6.3F);
        given(requester.request(p.barCode)).willReturn(p);
        String mail = "test@gmail.com";

        mockMvc.perform(
            post("/basket/" + p.barCode + "?mail="+ mail)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk());

        List<Basket> result = basketRepository.findAllByIdMail(mail);

        Assertions.assertEquals(result.size(), 1);

        Assertions.assertEquals(result.get(0).getId().getBarCode(),p.barCode);
    }

    @Test
    @DatabaseSetup("classpath:datasets/reset.xml")
    public void addInvalidProductToBasket() throws Exception {
        final long productID = 0L;
        given(requester.request(productID)).willReturn(null);
        String mail = "test@gmail.com";

        mockMvc.perform(
            post("/basket/" + productID + "?mail=" + mail)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound());

        List<Basket> result = basketRepository.findAllByIdMail(mail);
        Assertions.assertEquals(result.size(),0);
    }

    @Test
    @DatabaseSetup("classpath:datasets/basket.xml")
    public void deleteValidProductFromBasket() throws Exception {
        final long productID = 7622210449283L;

        String mail = "test@gmail.com";

        mockMvc.perform(
            delete("/basket/" + productID + "?mail=" + mail)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk());

        List<Basket> result = basketRepository.findAllByIdMail(mail);
        Assertions.assertEquals(result.size(),1);

    }

    @Test
    @DatabaseSetup("classpath:datasets/basket.xml")
    public void deleteInvalidProductFromBasket() throws Exception {
        final long productID = 0L;
        String mail = "test@gmail.com";

        mockMvc.perform(
            delete("/basket/" + productID + "?mail=" + mail)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound());

        List<Basket> result = basketRepository.findAllByIdMail(mail);
        Assertions.assertEquals(result.size(),2);

    }

    @Test
    @DatabaseSetup("classpath:datasets/basket.xml")
    public void getBasket() throws Exception {
        String mail = "test@gmail.com";

        mockMvc.perform(
            get("/basket" + "?mail=" + mail)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nutritionScore").value(5))
            .andExpect(jsonPath("$.classe").value("Mangeable"))
            .andExpect(jsonPath("$.color").value("yellow"))
            .andExpect(jsonPath("$.products[0].barCode").value("7613036669146"))
            .andExpect(jsonPath("$.products[1].barCode").value("7622210449283"));
    }

    @Test
    @DatabaseSetup("classpath:datasets/basket.xml")
    public void getInvalidBasket() throws Exception {
        String mail = "notfound@gmail.com";

        mockMvc.perform(
            get("/basket" + "?mail=" + mail)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound());
    }
}
