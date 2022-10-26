package com.finx.dropwizard.repositories;

import com.finx.domain.Product;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import ru.vyarus.guicey.jdbi3.installer.repository.JdbiRepository;
import ru.vyarus.guicey.jdbi3.tx.InTransaction;

import java.util.List;
import java.util.Optional;

@JdbiRepository
@InTransaction
public interface ProductRepository {
    @SqlUpdate("""
            INSERT INTO product(title, description, brand, price) VALUES (:title, :description, :brand, :price)
                """)
    @GetGeneratedKeys({"id", "title", "description", "brand", "price"})
    @RegisterBeanMapper(Product.class)
    Product insert(@BindBean Product Product);

    @SqlUpdate("""
            UPDATE Product
            SET title = :title,
            description = :description,
            brand = :brand,
            price = :price
            where id = :id
            """)
    @RegisterBeanMapper(Product.class)
    int update(@BindBean Product Product);

    @SqlQuery("""
            SELECT * FROM Product
            """)
    @RegisterBeanMapper(Product.class)
    List<Product> findAll();

    @SqlQuery("""
            SELECT * FROM Product WHERE id = :id
            """)
    @RegisterBeanMapper(Product.class)
    Optional<Product> findById(@Bind("id") Long id);

    @SqlUpdate("""
            DELETE FROM Product WHERE id = :id
            """)
    void deleteById(@Bind("id") Long id);
}
