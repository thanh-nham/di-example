package com.finx.dropwizard.resources;

import com.finx.domain.Product;
import com.finx.dropwizard.DropwizardApplication;
import com.finx.dropwizard.testing.AbstractComponentIT;
import com.finx.dropwizard.testing.TestContainerEnvironment;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import ru.vyarus.dropwizard.guice.test.jupiter.TestDropwizardApp;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestDropwizardApp(value = DropwizardApplication.class
        , randomPorts = true
        , config = "config-test.yml"
        , setup = TestContainerEnvironment.class)
public class ProductResourceTestContainer extends AbstractComponentIT {

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
        var result = response.readEntity(new GenericType<List<Product>>() {
        });

        // Then
        assertEquals(HttpStatus.SC_OK, response.getStatus());
        assertEquals(5, result.size());
        assertEquals(products.get(0), result.get(0));
    }

}
