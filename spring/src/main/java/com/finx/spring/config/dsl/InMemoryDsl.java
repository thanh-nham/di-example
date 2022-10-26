package com.finx.spring.config.dsl;

import com.finx.config.AbstractDsl;
import com.finx.domain.Product;

public class InMemoryDsl extends AbstractDsl {
    @Override
    protected Product saveAndUpdate(Product product) {
        return saveAndUpdate(product, Product.class);
    }
}
