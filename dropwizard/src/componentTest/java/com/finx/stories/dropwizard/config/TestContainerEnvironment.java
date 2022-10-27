package com.finx.stories.dropwizard.config;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import ru.vyarus.dropwizard.guice.test.jupiter.env.TestEnvironmentSetup;
import ru.vyarus.dropwizard.guice.test.jupiter.env.TestExtension;

public class TestContainerEnvironment implements TestEnvironmentSetup {

    static final PostgreSQLContainer<?> POSTGRES_CONTAINER;

    static {
        POSTGRES_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres"));
        POSTGRES_CONTAINER.start();
    }

    @Override
    public Object setup(TestExtension extension) {
        return extension.configOverrides(
                "database.url:%s".formatted(POSTGRES_CONTAINER.getJdbcUrl()),
                "database.user:%s".formatted(POSTGRES_CONTAINER.getUsername()),
                "database.password:%s".formatted(POSTGRES_CONTAINER.getPassword())
                );
    }
}
