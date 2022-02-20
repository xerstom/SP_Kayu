package com.boulec.kayu.services.off;

import com.boulec.kayu.configurations.RestTemplateClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class OFFRequester {
    static final String baseUrl = "https://fr.openfoodfacts.org/api/v0/produit/";
    static final String[] fields = {
            "id",
            "generic_name_fr",
            "energy_100g",
            "saturated-fat_100g",
            "sugars_100g",
            "salt_100g",
            "fiber_100g",
            "proteins_100g"
    };

    private final String reqFields;

    @Autowired
    private RestTemplate restTemplate;

    public OFFRequester() {
        this.reqFields = buildFields();
    }

    private String buildUrl(String id) {
        return baseUrl + id + ".json" + reqFields;
    }

    private String buildFields() {
        return "?fields=" + String.join(",", fields);
    }

    public OFFProduct request(String id) {
        String url = buildUrl(id);
        System.out.println(url);
        try{
            URI uri = new URI(url);
            ResponseEntity<OFFResponse> result = restTemplate.getForEntity(uri, OFFResponse.class);
            OFFResponse response = result.getBody();
            System.out.println(response);

            return response.product;
        }
        catch (Exception e){
            System.err.println(e);
        }
        return null;
    }
}
