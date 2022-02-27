package com.boulec.kayu.services.mappers;

import com.boulec.kayu.dtos.ProductDto;
import com.boulec.kayu.models.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto asDto(Product element);
}
