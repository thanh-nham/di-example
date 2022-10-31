package com.finx.dropwizard.testing;

import com.finx.dropwizard.DropwizardApplication;
import com.finx.dropwizard.repositories.ProductRepository;
import com.finx.dropwizard.testing.environment.TestContainerEnvironment;
import ru.vyarus.dropwizard.guice.test.jupiter.TestGuiceyApp;

import javax.inject.Inject;

@TestGuiceyApp(
        value = DropwizardApplication.class
        , setup = TestContainerEnvironment.class
        , config = "config-test.yml")
public abstract class AbstractRepositoryIT extends AbstractIT {
    @Inject
    protected ProductRepository productRepository;
}
