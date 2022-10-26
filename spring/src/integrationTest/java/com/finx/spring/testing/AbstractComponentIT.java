package com.finx.spring.testing;

import com.finx.config.AbstractDsl;
import com.finx.mappers.ProductMapper;
import com.finx.spring.config.dsl.InMemoryDsl;
import com.finx.spring.config.dsl.PersistentDsl;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles({"test"})
@Import({PersistentDsl.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractComponentIT extends AbstractIT {
    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    protected ProductMapper productMapper;

    protected MockMvc mockMvc;

    protected AbstractDsl inMemoryDsl = new InMemoryDsl();

    @BeforeEach
    void setUp() {
        super.septUp();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .build();
    }


}
