package com.finx.dropwizard.repositories;

import com.finx.dropwizard.testing.AbstractRepositoryIT;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductRepositoryTest extends AbstractRepositoryIT {

    @Test
    void shouldFindAllProduct() {
        // Given
        var products = IntStream.rangeClosed(1,5)
                .mapToObj(i -> dsl.product())
                .toList();

        // When
        var result = productRepository.findAll();

        // Then
        assertEquals(products, result);
    }

    @Test
    void shouldFindOneProductById() {
        // Given
        var product = dsl.product();

        // When
        var result = productRepository.findById(product.getId());

        // Then
        assertTrue(result.isPresent());
        assertEquals(product, result.get());
    }

    @Test
    void shouldSaveProductSuccessfully() {
        // Given
        var product = dsl.product();

        // When
        var result = productRepository.insert(product);
        product.setId(result.getId());

        // Then
        assertEquals(product, result);
    }

    @Test
    void shouldUpdateSuccessfully() {
        // Given
        var product = dsl.product();

        // When
        var result = productRepository.update(product);

        // Then
        assertTrue(result > 0);
    }

    @Test
    void shouldDeleteProductSuccessfully() {
        // Given
        var product = dsl.product();

        // When
        productRepository.deleteById(product.getId());

    }
}
