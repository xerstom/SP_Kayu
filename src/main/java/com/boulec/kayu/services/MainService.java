package com.boulec.kayu.services;

import com.boulec.kayu.dtos.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class MainService {

    @Autowired
    private RestTemplate restTemplate;

    public ProductDto getProductService(String id) {
        final String baseUrl = "https://fr.openfoodfacts.org/api/v0/produit/" + id + ".json";
        try{
            URI uri = new URI(baseUrl);
            ResponseEntity<ProductDto> result = restTemplate.getForEntity(uri, ProductDto.class);
            ProductDto body = result.getBody();
            System.out.println(result);
            return body;
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
