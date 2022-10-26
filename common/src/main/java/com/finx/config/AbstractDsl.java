package com.finx.config;

import com.finx.domain.Product;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class AbstractDsl {
    private final Map<Class<?>, List<Object>> entities = new HashMap<>();

    private final Random random = new Random();

    public final void cleanUp() {
        entities.clear();
    }

    protected void add(Object entity) {
        var value = entities.get(entity.getClass());
        if (CollectionUtils.isEmpty(value)) {
            List<Object> list = new ArrayList<>();
            list.add(entity);
            entities.put(entity.getClass(), list);
        } else {
            value.add(entity);
        }
    }

    protected <T> T saveAndUpdate(Object value, Class<T> clazz) {
        add(value);
        return clazz.cast(value);
    }

    protected <T> int nextIndex(Class<T> clazz) {
        var list = entities.get(clazz);
        return CollectionUtils.isNotEmpty(list) ? list.size() + 1 : 1;
    }

    protected <T> int lastIndex(Class<T> clazz) {
        var list = entities.get(clazz);
        return CollectionUtils.isNotEmpty(list) ? list.size() : 1;
    }

    protected <T> T lastOrNull(Class<T> clazz) {
        var list = entities.get(clazz);
        return CollectionUtils.isNotEmpty(list) ? clazz.cast(list.get(list.size() - 1)) : null;
    }

    protected <T> Integer lastIdOrNull(Class<T> clazz) {
        try {
            var list = entities.get(clazz);
            if (CollectionUtils.isNotEmpty(list)) {
                var entity = list.get(list.size() - 1);
                var method = entity.getClass().getDeclaredMethod("getId");
                method.setAccessible(true);
                return (Integer) method.invoke(entity);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException(String.format("Fail to get Id of entity %s", clazz.getSimpleName()));
        }
        return null;
    }

    protected <T> T last(Object obj, Class<T> clazz) {
        var result = lastOrNull(obj.getClass());
        if (result == null) {
            throw new IllegalArgumentException(String.format("No entity found for class %s", obj.getClass().getSimpleName()));
        }
        return clazz.cast(result);
    }

    // Product
    public Product product() {
        int index = nextIndex(Product.class);
        var product = new Product(
                "title %s".formatted(index),
                "description %s".formatted(index),
                "brand %s".formatted(index),
                BigDecimal.valueOf(random.nextDouble())
        );
        return saveAndUpdate(product);
    }

    protected abstract Product saveAndUpdate(Product product);

}
