package com.finx.spring.testing;

import com.finx.config.AbstractDsl;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;

public abstract class AbstractIT {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    protected AbstractDsl dsl;

    private List<String> tables;

    @BeforeEach
    void septUp() {
        if (CollectionUtils.isEmpty(tables))
        tables = jdbcTemplate.queryForList(
                "SELECT tablename FROM pg_tables WHERE schemaname = 'public' AND tablename NOT IN ('databasechangelog', 'databasechangeloglock');", String.class
        );
        if (!CollectionUtils.isEmpty(tables)) {
            jdbcTemplate.execute(String.format("TRUNCATE TABLE %s;", String.join(", ", tables)));
        }
        dsl.cleanUp();
    }

}
