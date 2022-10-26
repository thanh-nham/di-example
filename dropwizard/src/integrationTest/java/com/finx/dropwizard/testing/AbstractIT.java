package com.finx.dropwizard.testing;

import com.finx.config.AbstractDsl;
import com.finx.dropwizard.config.DropwizardConfiguration;
import com.google.inject.name.Named;
import io.dropwizard.setup.Environment;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.apache.commons.collections4.CollectionUtils;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractIT {
    @Inject
    @Named("PersistentDsl")
    protected AbstractDsl dsl;

    @Inject
    @Named("InMemoryDsl")
    protected AbstractDsl inMemoryDsl;

    @Inject
    private Jdbi jdbi;

    private List<String> tables;

    @BeforeAll
    static void beforeAll(DropwizardConfiguration configuration, Environment environment) {
        var datasource = configuration.getDatabase().build(environment.metrics(), "migration");
        try (var connection = datasource.getConnection())
        {
            Liquibase migrator = new Liquibase("db.changelog-master.yaml", new ClassLoaderResourceAccessor(), new JdbcConnection(connection));
            migrator.update("");
        } catch (SQLException | LiquibaseException e) {
            throw new RuntimeException("Failed to start", e);
        }
    }

    @AfterEach
    void tearDown() {
        jdbi.withHandle(handle -> {
            if (CollectionUtils.isEmpty(tables)) {
                tables = handle.createQuery("SELECT tablename FROM pg_tables WHERE schemaname = 'public' AND tablename NOT IN ('databasechangelog', 'databasechangeloglock')")
                        .mapTo(String.class)
                        .list();
            }
            if (CollectionUtils.isNotEmpty(tables)) {
                return handle.createUpdate("TRUNCATE TABLE %s".formatted(String.join(", ", tables))).execute();
            }
            return 0;
        });
        dsl.cleanUp();
    }
}
