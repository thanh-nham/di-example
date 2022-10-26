package com.finx.dropwizard;

import com.finx.dropwizard.config.ApplicationConfigModule;
import com.finx.dropwizard.config.DropwizardConfiguration;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;
import ru.vyarus.guicey.jdbi3.JdbiBundle;

public class DropwizardApplication extends Application<DropwizardConfiguration> {

    public static void main(String[] args) throws Exception {
        new DropwizardApplication().run(args);
    }

    @Override
    public String getName() {
        return "dropwizard";
    }

    @Override
    public void initialize(Bootstrap<DropwizardConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<>() {
            @Override
            public PooledDataSourceFactory getDataSourceFactory(DropwizardConfiguration configuration) {
                return configuration.getDatabase();
            }
            @Override
            public String getMigrationsFileName() {
                return "db.changelog-master.yaml";
            }
        });
        bootstrap.addBundle(GuiceBundle.builder()
                .enableAutoConfig(getClass().getPackage().getName())
                .bundles(JdbiBundle.<DropwizardConfiguration>forDatabase((conf, env) -> conf.getDatabase()))
                .modules(new ApplicationConfigModule())
                .build());
    }

    @Override
    public void run(DropwizardConfiguration configuration, Environment environment) {

    }
}
