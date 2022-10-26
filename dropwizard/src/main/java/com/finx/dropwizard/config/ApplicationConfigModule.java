package com.finx.dropwizard.config;

import com.finx.config.AbstractDsl;
import com.finx.dropwizard.config.dsl.InMemoryDsl;
import com.finx.dropwizard.config.dsl.PersistentDsl;
import com.finx.mappers.ProductMapper;
import com.finx.mappers.impl.ProductMapperImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

public class ApplicationConfigModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AbstractDsl.class).annotatedWith(Names.named("PersistentDsl"))
                .to(PersistentDsl.class)
                .in(Singleton.class);
        bind(AbstractDsl.class).annotatedWith(Names.named("InMemoryDsl"))
                .to(InMemoryDsl.class)
                .in(Singleton.class);
    }

    @Provides
    public ProductMapper productMapper() {
        return new ProductMapperImpl();
    }
}
