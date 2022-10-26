package com.finx.spring.services;

import com.finx.domain.Product;
import com.finx.spring.exceptions.NotFoundException;
import com.finx.spring.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(long id) {
        return repository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public Product saveProduct(Product product) {
        return repository.saveAndFlush(product);
    }

    public void deleteProduct(long id) {
        repository.deleteById(id);
    }
}
