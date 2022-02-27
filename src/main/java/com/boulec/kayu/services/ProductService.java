package com.boulec.kayu.services;

import com.boulec.kayu.dtos.ProductDto;
import com.boulec.kayu.models.Product;
import com.boulec.kayu.repositories.ProductRepository;
import com.boulec.kayu.services.factory.ProductFactory;
import com.boulec.kayu.services.mappers.ProductMapper;
import com.boulec.kayu.services.off.OFFProduct;
import com.boulec.kayu.services.off.OFFRequester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private OFFRequester requester;

    @Autowired
    private ScoreCompute scoreCompute;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public Optional<ProductDto> getOne(long id) {
        Product p = this.getOrFetch(id);
        if (p == null) {
            return Optional.empty();
        }
        return Optional.of(productMapper.asDto(p));
    }

    /**
     * Get a product from the DB or fetch from OpenFoodFact if doesn't exist
     * If fetched from OFF, add to the DB
     *
     * @param id
     * @return
     */
    public Product getOrFetch(long id) {
        Optional<Product> p = productRepository.findById(id);
        if (p.isPresent()) {
            return p.get();
        }

        OFFProduct product = this.requester.request(id);
        if (product == null) {
            return null;
        }

        int nutriScore = scoreCompute.computeNutriScore(product);
        List<String> classAndColor = scoreCompute.computeClassAndColor(nutriScore);

        Product productModel = ProductFactory.toProductModel(product, nutriScore,
                classAndColor.get(0),
                classAndColor.get(1));

        try {
            productRepository.save(productModel);
        } catch (DataAccessException e) {
            System.out.println(e);
            // do nothing since it's not blocking
        }
        return productModel;
    }
}
