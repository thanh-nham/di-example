package com.finx.stories.dropwizard.steps;

import com.finx.config.AbstractDsl;
import com.finx.domain.Product;
import org.apache.http.HttpStatus;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductStep extends Steps {
    private final AbstractDsl dsl;
    private final Client client;
    private final String apiEndpoint;

    private Product product;
    private Response response;

    public ProductStep(AbstractDsl dsl, Client client, int localPort) {
        this.dsl = dsl;
        this.client = client;
        this.apiEndpoint = "http://localhost:%s/products".formatted(localPort);
    }

    @Given("Have a product")
    public void createProduct() {
        product = dsl.product();
    }

    @When("User call delete product by Id")
    public void callApiDeleteProductById() {
        this.response = client.target("%s/%s".formatted(apiEndpoint, product.getId()))
                .request()
                .buildGet()
                .invoke();
    }

    @Then("Product should be delete successfully")
    public void checkProductDeleteSuccessfully() {
        assertEquals(HttpStatus.SC_OK, response.getStatus());
    }
}
