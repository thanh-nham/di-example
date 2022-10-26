package com.finx.dropwizard.testing;

import ru.vyarus.dropwizard.guice.test.jupiter.env.TestEnvironmentSetup;
import ru.vyarus.dropwizard.guice.test.jupiter.env.TestExtension;

public class TestContainerEnvironment extends AbstractContainer implements TestEnvironmentSetup {

    @Override
    public Object setup(TestExtension extension) {
        return extension.configOverrides(
                "database.url:%s".formatted(POSTGRES_CONTAINER.getJdbcUrl()),
                "database.user:%s".formatted(POSTGRES_CONTAINER.getUsername()),
                "database.password:%s".formatted(POSTGRES_CONTAINER.getPassword())
                );
    }
}
