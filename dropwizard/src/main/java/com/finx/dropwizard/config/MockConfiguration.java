package com.finx.dropwizard.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class MockConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty
    private List<Account> accounts;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public record Account(String accessKey, String secretKey) {

    }
}
