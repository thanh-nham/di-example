package com.finx.stories.dropwizard;


import com.finx.stories.dropwizard.steps.ProductStep;

import java.util.List;

public class ProductStoryTest extends AbstractStoryTest {
    @Override
    protected List<?> getStepClasses() {
        return List.of(new ProductStep(dsl, client, localPort));
    }

    @Override
    public List<String> storyPaths() {
        return List.of("stories/delete_product.story");
    }
}
