package com.finx.mappers.impl;

import com.finx.domain.Product;
import com.finx.dtos.ProductDto;
import com.finx.mappers.ProductMapper;

import java.util.Objects;

public class ProductMapperImpl implements ProductMapper {
    @Override
    public ProductDto toDto(Product product) {
        return new ProductDto(product.getId()
                , product.getTitle()
                , product.getDescription()
                , product.getBrand()
                , product.getPrice());
    }

    @Override
    public Product toProduct(ProductDto dto) {
        return new Product(dto.title(), dto.description(), dto.brand(), dto.price());
    }

    @Override
    public void updateProductFromDto(Product product, ProductDto dto) {
        if (Objects.nonNull(dto.title())) {
            product.setTitle(dto.title());
        }
        if (Objects.nonNull(dto.description())) {
            product.setDescription(dto.description());
        }
        if (Objects.nonNull(dto.brand())) {
            product.setBrand(dto.brand());
        }
        if (Objects.nonNull(dto.price())) {
            product.setPrice(dto.price());
        }
    }
}
