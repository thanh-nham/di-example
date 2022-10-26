package com.finx.dropwizard.resources;

import com.finx.domain.Product;
import com.finx.dropwizard.DropwizardApplication;
import com.finx.dropwizard.testing.AbstractComponentIT;
import com.finx.dropwizard.testing.environment.TestContainerEnvironment;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import ru.vyarus.dropwizard.guice.test.jupiter.TestDropwizardApp;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestDropwizardApp(value = DropwizardApplication.class
        , randomPorts = true
        , setup = TestContainerEnvironment.class
        , config = "config-test.yml")
public class ProductResourceTest extends AbstractComponentIT {

    private final String PRODUCT_URI = "%s/products".formatted(baseUrl);

    @Test
    void shouldFetchAllProductSuccessfully() {
        // Given
        var products = IntStream.rangeClosed(1, 5)
                .mapToObj(i -> dsl.product())
                .toList();

        // When
        var response = client.target(PRODUCT_URI)
                .request()
                .buildGet()
                .invoke();
        var result = response.readEntity(new GenericType<List<Product>>() {});

        // Then
        assertEquals(HttpStatus.SC_OK, response.getStatus());
        assertEquals(5, result.size());
        assertEquals(products.get(0), result.get(0));
    }

    @Test
    void shouldFetchAOneProductById() {
        // Given
        var product = dsl.product();

        // When
        var response = client.target("%s/%s".formatted(PRODUCT_URI, product.getId()))
                .request()
                .buildGet()
                .invoke();
        var result = response.readEntity(Product.class);

        // Then
        assertEquals(HttpStatus.SC_OK, response.getStatus());
        assertEquals(product, result);
    }

    @Test
    public void shouldSaveProductSuccessfully() {
        // Given
        var product = inMemoryDsl.product();

        // When
        var response = client.target(PRODUCT_URI)
                .request()
                .buildPost(Entity.entity(product, MediaType.APPLICATION_JSON))
                .invoke();
        var result = response.readEntity(Product.class);

        // Then
        assertEquals(HttpStatus.SC_CREATED, response.getStatus());
        assertNotNull(result.getId());
    }

    @Test
    public void shouldUpdateProductSuccessfully() {
        // Given
        var person = dsl.product();

        // When
        var response = client.target("%s/%s".formatted(PRODUCT_URI, person.getId()))
                .request()
                .buildPut(Entity.entity(person, MediaType.APPLICATION_JSON))
                .invoke();
        var result = response.readEntity(Product.class);

        // Then
        assertEquals(HttpStatus.SC_OK, response.getStatus());
    }

    @Test
    public void shouldDeleteSuccessfully() {
        // Given
        var person = dsl.product();

        // When
        var response = client.target("%s/%s".formatted(PRODUCT_URI, person.getId()))
                .request()
                .buildDelete()
                .invoke();

        // Then
        assertEquals(HttpStatus.SC_OK, response.getStatus());
    }

}
