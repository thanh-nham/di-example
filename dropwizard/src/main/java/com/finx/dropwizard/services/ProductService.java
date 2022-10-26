package com.finx.dropwizard.services;

import com.finx.domain.Product;
import com.finx.dropwizard.repositories.ProductRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Singleton
public class ProductService {
    private final ProductRepository repository;

    @Inject
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id [%s] not found!".formatted(id)));
    }

    public Product insert(Product Product) {
        return repository.insert(Product);
    }

    public void update(Product Product) {
        repository.update(Product);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
