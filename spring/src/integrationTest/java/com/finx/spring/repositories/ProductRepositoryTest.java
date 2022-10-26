package com.finx.spring.repositories;

import com.finx.spring.testing.AbstractRepositoryIT;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductRepositoryTest extends AbstractRepositoryIT {
    @Test
    void shouldFindAll() {
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
    void shouldFindById() {
        // Given
        var product = dsl.product();

        // When
        var result = productRepository.findById(product.getId());

        // Then
        assertTrue(result.isPresent());
        assertEquals(product, result.get());
    }

    @Test
    void shouldDeleteById() {
        // Given
        var product = dsl.product();

        // When
        productRepository.deleteById(product.getId());

        // Then
        assertTrue(productRepository.findById(product.getId()).isEmpty());
    }

    @Test
    void shouldSaveProductSuccessfully() {
        // Given
        var product = dsl.product();

        // When
        var result = productRepository.saveAndFlush(product);

        // Then
        assertEquals(product, result);
    }

}
