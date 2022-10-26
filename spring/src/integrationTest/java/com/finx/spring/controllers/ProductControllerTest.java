package com.finx.spring.controllers;

import com.finx.spring.testing.AbstractComponentIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;

import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
public class ProductControllerTest extends AbstractComponentIT {

    private static final String PRODUCT_API = "/products";

    @Test
    void shouldFetchAllProduct() throws Exception {
        // Given
        var products = IntStream.rangeClosed(1, 5)
                .mapToObj(i -> dsl.product())
                .toList();

        // When
        var response = mockMvc.perform(get(PRODUCT_API)
                .contentType(MediaType.APPLICATION_JSON));
        // Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id", is(products.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].title", is(products.get(0).getTitle())))
                .andExpect(jsonPath("$[0].description", is(products.get(0).getDescription())))
                .andExpect(jsonPath("$[0].brand", is(products.get(0).getBrand())));

    }

    @Test
    public void shouldFetchOneProductById() throws Exception {
        // Given
        var product = dsl.product();
        final long id = product.getId();

        // When
        var response = mockMvc.perform(get(String.format("%s/%s", PRODUCT_API, id))
                .contentType(MediaType.APPLICATION_JSON));
        // Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(product.getId().intValue())))
                .andExpect(jsonPath("$.title", is(product.getTitle())))
                .andExpect(jsonPath("$.description", is(product.getDescription())))
                .andExpect(jsonPath("$.brand", is(product.getBrand())));
    }

    @Test
    public void shouldReturnNotFoundWhenFetchOneProductById() throws Exception {
        // Given
        final int id = -1;

        // When
        var response = mockMvc.perform(get(String.format("%s/%s", PRODUCT_API, id))
                .contentType(MediaType.APPLICATION_JSON));
        // Then
        response.andExpect(status().isNotFound());
    }

}
