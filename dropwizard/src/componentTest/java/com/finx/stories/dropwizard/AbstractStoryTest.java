package com.finx.stories.dropwizard;

import com.finx.config.AbstractDsl;
import com.finx.dropwizard.DropwizardApplication;
import com.finx.dropwizard.config.DropwizardConfiguration;
import com.finx.stories.dropwizard.config.TestContainerEnvironment;
import com.google.inject.name.Named;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.apache.commons.collections4.CollectionUtils;
import org.glassfish.jersey.client.ClientProperties;
import org.jbehave.core.ConfigurableEmbedder;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.vyarus.dropwizard.guice.test.ClientSupport;
import ru.vyarus.dropwizard.guice.test.jupiter.TestDropwizardApp;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import java.sql.SQLException;
import java.util.List;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;

@TestDropwizardApp(value = DropwizardApplication.class
        , randomPorts = true
        , setup = TestContainerEnvironment.class
        , config = "config-test.yml")
public abstract class AbstractStoryTest extends ConfigurableEmbedder {

    @Inject
    @Named("PersistentDsl")
    protected AbstractDsl dsl;

    @Inject
    private Jdbi jdbi;
    private List<String> tables;

    protected static Client client;
    protected static int localPort;

    @BeforeAll
    static void beforeAll(ClientSupport clientSupport, DropwizardConfiguration configuration, Environment environment) {
        client = new JerseyClientBuilder(environment)
                .withProperty(ClientProperties.READ_TIMEOUT, 30_000)
                .build("test client");
        localPort = clientSupport.getPort();
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

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration()
                .useStoryLoader(new LoadFromClasspath(this.getClass()))
                .useStoryReporterBuilder(new StoryReporterBuilder()
                        .withCodeLocation(codeLocationFromClass(this.getClass()))
                        .withFormats(CONSOLE));
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), getStepClasses());
    }

    protected abstract List<?> getStepClasses();

    @Override
    public void run() {

    }

    @Test
    protected void test() {
        Embedder embedder = configuredEmbedder();
        try {
            embedder.runStoriesAsPaths(storyPaths());
        } finally {
            embedder.generateSurefireReport();
        }
    }
    public abstract List<String> storyPaths();
}
