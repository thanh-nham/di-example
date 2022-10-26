package com.finx.mappers;

import com.finx.domain.Product;
import com.finx.dtos.ProductDto;

public interface ProductMapper {
    ProductDto toDto(Product product);
    Product toProduct(ProductDto dto);
    void updateProductFromDto(Product product, ProductDto dto);
}
