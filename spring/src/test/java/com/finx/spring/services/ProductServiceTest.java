package com.finx.spring.services;

import com.finx.spring.repositories.ProductRepository;
import com.finx.spring.testing.AbstractServiceTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest extends AbstractServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    ProductService service;

    @Test
    void shouldFindAllProductSuccessfully() {
        // Given
        var products = IntStream.rangeClosed(1, 5)
                .mapToObj(i -> dsl.product())
                .toList();

        // When
        when(repository.findAll()).thenReturn(products);
        var result = service.findAll();

        // Then
        verify(repository).findAll();
        assertEquals(products, result);
    }

    @Test
    void shouldSaveProductSuccessfully() {
        // Given
        var product = dsl.product();

        // When
        when(repository.saveAndFlush(product)).thenReturn(product);
        var result = service.saveProduct(product);

        // Then
        verify(repository).saveAndFlush(product);
        assertEquals(product, result);
    }

    @Test
    void shouldFindProductById() {
        // Given
        var product = dsl.product();
        product.setId(1L);

        // When
        when(repository.findById(product.getId())).thenReturn(Optional.of(product));
        var result = service.findById(product.getId());

        // Then
        verify(repository).findById(product.getId());
        assertEquals(product, result);
    }

    @Test
    void shouldDeleteProductById() {
        // Given
        var product = dsl.product();
        product.setId(1L);

        // When
        doNothing().when(repository).deleteById(product.getId());
        service.deleteProduct(product.getId());

        // Then
        verify(repository).deleteById(product.getId());
    }

}
