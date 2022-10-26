package com.finx.spring.testing;

import com.finx.config.AbstractDsl;
import com.finx.spring.config.dsl.InMemoryDsl;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractServiceTest {

    protected AbstractDsl dsl = new InMemoryDsl();

    @BeforeEach
    public void setup() {
        dsl.cleanUp();
    }
}
