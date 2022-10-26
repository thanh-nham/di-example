package com.finx.spring.controllers;

import com.finx.domain.Product;
import com.finx.dtos.ProductDto;
import com.finx.mappers.ProductMapper;
import com.finx.spring.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;
    private final ProductMapper mapper;

    @Autowired
    public ProductController(ProductService service, ProductMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<ProductDto> findAll() {
        return service.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Long id) {
        return mapper.toDto(service.findById(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        Product product = mapper.toProduct(productDto);
        return mapper.toDto(service.saveProduct(product));
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@RequestBody ProductDto productDto, @PathVariable Long id) {
        Product product = service.findById(id);
        mapper.updateProductFromDto(product, productDto);
        return mapper.toDto(service.saveProduct(product));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
    }

}
