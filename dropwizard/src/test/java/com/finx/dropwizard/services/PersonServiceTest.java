package com.finx.dropwizard.services;

import com.finx.dropwizard.repositories.ProductRepository;
import com.finx.dropwizard.testing.AbstractServiceTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest extends AbstractServiceTest {
    @Mock
    ProductRepository repository;

    @InjectMocks
    ProductService service;

    @Test
    void shouldFindAllProduct() {
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
    void shouldFindOnePersonById() {
        // Given
        var product = dsl.product();

        // When
        when(repository.findById(product.getId())).thenReturn(Optional.of(product));
        var result = service.findById(product.getId());

        // Then
        verify(repository).findById(product.getId());
        assertNotNull(result);
        assertEquals(product, result);
    }

    @Test
    void shouldInsertPersonSuccessfully() {
        // Given
        var product = dsl.product();

        // When
        when(repository.insert(product)).thenReturn(product);
        var result = service.insert(product);

        // Then
        verify(repository).insert(product);
        assertEquals(product, result);
    }

}