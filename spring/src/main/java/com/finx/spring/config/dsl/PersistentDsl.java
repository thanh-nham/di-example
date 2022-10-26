package com.finx.spring.config.dsl;

import com.finx.config.AbstractDsl;
import com.finx.domain.Product;
import com.finx.spring.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PersistentDsl extends AbstractDsl {
    @Autowired
    private ProductRepository productRepository;

    @Override
    protected Product saveAndUpdate(Product product) {
        return saveAndUpdate(productRepository.saveAndFlush(product), Product.class);
    }
}
