package com.boulec.kayu.services;

import com.boulec.kayu.dtos.ProductDto;
import com.boulec.kayu.services.factory.ProductFactory;
import com.boulec.kayu.services.off.OFFProduct;
import com.boulec.kayu.services.off.OFFRequester;
import com.boulec.kayu.services.off.OFFResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class ProductService {

    @Autowired
    private OFFRequester requester;

    @Autowired
    private RestTemplate restTemplate;

    public ProductDto getOne(String id) {
        OFFProduct product = this.requester.request(id);

        if (product == null) {
            return null;
        }
        // requete http
        // OFF PRoduct
        // calcul nutri score
        // creer Product DTO
        // return
        int nutriScore = computeNutriScore(product);

        return ProductFactory.toProductDTO(product, nutriScore);
    }

    private int computeNutriScore(OFFProduct p){
        return 1;
    }
}
