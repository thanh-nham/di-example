package com.finx.spring.testing;


import com.finx.spring.config.dsl.PersistentDsl;
import com.finx.spring.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"test"})
@Import({PersistentDsl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest()
public abstract class AbstractRepositoryIT extends AbstractIT {

    @Autowired
    protected ProductRepository productRepository;
}
