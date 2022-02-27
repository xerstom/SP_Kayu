package com.boulec.kayu.services.factory;

import com.boulec.kayu.models.Product;
import com.boulec.kayu.services.off.OFFProduct;

public class ProductFactory {
    public static Product toProductModel(OFFProduct product, int nutriScore, String classe, String color) {
        return new Product(product.barCode, product.name, nutriScore, classe, color);
    }
}
