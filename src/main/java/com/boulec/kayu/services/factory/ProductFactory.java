package com.boulec.kayu.services.factory;

import com.boulec.kayu.dtos.ProductDto;
import com.boulec.kayu.services.off.OFFProduct;

public class ProductFactory {
    public static ProductDto toProductDTO(OFFProduct product, int nutriScore) {
        return new ProductDto(product.barCode, product.name, nutriScore);
    }
}
