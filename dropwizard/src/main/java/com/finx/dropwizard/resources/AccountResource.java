package com.finx.dropwizard.resources;

import com.finx.dropwizard.config.MockConfiguration;
import ru.vyarus.dropwizard.guice.module.yaml.bind.Config;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Path("/accounts")
public class AccountResource {


    private final MockConfiguration configuration;

    @Inject
    public AccountResource(@Config MockConfiguration configuration) {
        this.configuration = configuration;
    }

    @GET
    public List<MockConfiguration.Account> findAll() {
        return configuration.getAccounts();
    }
}
