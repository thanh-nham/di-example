package com.finx.dropwizard.testing;

import com.finx.dropwizard.repositories.ProductRepository;

import javax.inject.Inject;

public abstract class AbstractRepositoryIT extends AbstractIT {
    @Inject
    protected ProductRepository productRepository;
}
