package com.finx.dropwizard.testing;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class AbstractContainer {
    static final PostgreSQLContainer<?> POSTGRES_CONTAINER;

    static {
        POSTGRES_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres"));
        POSTGRES_CONTAINER.start();
    }
}
