package com.finx.dropwizard.config.dsl;

import com.finx.config.AbstractDsl;
import com.finx.domain.Product;
import com.finx.dropwizard.repositories.ProductRepository;

import javax.inject.Inject;

public class PersistentDsl extends AbstractDsl {

    @Inject
    private ProductRepository productRepository;

    @Override
    protected Product saveAndUpdate(Product product) {
        if(product.getId() != null) {
            productRepository.update(product);
            return saveAndUpdate(product, Product.class);
        }
        return saveAndUpdate(productRepository.insert(product), Product.class);
    }
}
