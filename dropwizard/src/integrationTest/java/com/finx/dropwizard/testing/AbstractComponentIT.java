package com.finx.dropwizard.testing;

import com.finx.dropwizard.DropwizardApplication;
import com.finx.dropwizard.testing.environment.TestContainerEnvironment;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.client.ClientProperties;
import org.junit.jupiter.api.BeforeAll;
import ru.vyarus.dropwizard.guice.test.ClientSupport;
import ru.vyarus.dropwizard.guice.test.jupiter.TestDropwizardApp;

import javax.ws.rs.client.Client;

@TestDropwizardApp(value = DropwizardApplication.class
        , randomPorts = true
        , setup = TestContainerEnvironment.class
        , config = "config-test.yml")
public abstract class AbstractComponentIT extends AbstractIT {

    protected static Client client;
    protected static String baseUrl;

    @BeforeAll
    static void beforeAll(ClientSupport clientSupport, Environment environment) {
        client = new JerseyClientBuilder(environment)
                .withProperty(ClientProperties.READ_TIMEOUT, 30_000)
                .build("test client");
        baseUrl = "http://localhost:%s".formatted(clientSupport.getPort());
    }

}
