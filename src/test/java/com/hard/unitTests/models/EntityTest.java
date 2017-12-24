package com.hard.unitTests.models;

import com.hard.models.Entity;
import org.junit.Assert;
import org.junit.Test;

public class EntityTest {
    @Test
    public void shouldCreateEntityWithDefaultValues_Test() {
        Entity entity = new Entity();

        Assert.assertEquals(0, entity.getId());
        Assert.assertEquals(null, entity.getName());
    }

    @Test
    public void shouldCreateEntityWithCustomValuesViaConstructor1_Test() {
        Entity entity = new Entity("entity1");

        Assert.assertEquals(0, entity.getId());
        Assert.assertEquals("entity1", entity.getName());
    }

    @Test
    public void shouldCreateEntityWithCustomValuesViaConstructor2_Test() {
        Entity entity = new Entity(1, "entity1");

        Assert.assertEquals(1, entity.getId());
        Assert.assertEquals("entity1", entity.getName());
    }

    @Test
    public void shouldCreateEntityWithCustomValuesViaSetters_Test() {
        Entity entity = new Entity();

        entity.setId(1);
        entity.setName("entity1");

        Assert.assertEquals(1, entity.getId());
        Assert.assertEquals("entity1", entity.getName());
    }
}
