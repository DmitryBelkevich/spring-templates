package com.hard.integrationTests.services;

import com.hard.config.MvcConfig;
import com.hard.models.Entity;
import com.hard.services.EntityService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {
                MvcConfig.class,
        }
)
@WebAppConfiguration
@Sql(
        value = "classpath:sql_scripts/init.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(
        value = "classpath:sql_scripts/destroy.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
public class EntityServiceTest {
    // TODO - negative cases
    // TODO - to do check on all field in all tests

    @Autowired
    private EntityService entityService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void shouldReturnAllEntities_Test() {
        Collection<Entity> collection = entityService.getAll();
        List<Entity> entities = new ArrayList<>(collection);

        long size = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM entity", Long.class);
        Assert.assertEquals(size, entities.size());
    }

    @Test
    public void shouldReturnEntity_Test() {
        Entity entity1 = entityService.getById(1);
        Assert.assertEquals(1, entity1.getId());
        Assert.assertEquals("entity1", entity1.getName());

        Entity entity2 = entityService.getById(2);
        Assert.assertEquals(2, entity2.getId());
        Assert.assertEquals("entity2", entity2.getName());

        Entity entity3 = entityService.getById(3);
        Assert.assertEquals(3, entity3.getId());
        Assert.assertEquals("entity3", entity3.getName());
    }

    @Test
    public void shouldAddEntity_Test() {
        entityService.add(new Entity("entity4"));
        entityService.add(new Entity("entity5"));
        entityService.add(new Entity("entity6"));

        long size = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM entity", Long.class);
        Assert.assertEquals(6, size);
    }

    @Test
    public void shouldAddEntities_Test() {
        Collection<Entity> entities = new ArrayList<>();

        entities.add(new Entity("entity4"));
        entities.add(new Entity("entity5"));
        entities.add(new Entity("entity6"));

        entityService.add(entities);

        long size = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM entity", Long.class);
        Assert.assertEquals(6, size);
    }

    @Test
    public void shouldUpdateEntity_Test() {
        entityService.update(1, new Entity("entity4"));
        entityService.update(2, new Entity("entity5"));
        entityService.update(3, new Entity("entity6"));

        long size = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM entity", Long.class);
        Assert.assertEquals(3, size);
    }

    @Test
    public void shouldUpdateEntities_Test() {
        Collection<Entity> entities = new ArrayList<>();

        entities.add(new Entity(1, "entity4"));
        entities.add(new Entity(2, "entity5"));
        entities.add(new Entity(3, "entity6"));

        entityService.update(entities);

        long size = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM entity", Long.class);
        Assert.assertEquals(3, size);
    }

    @Test
    public void shouldDeleteEntity_Test() {
        entityService.delete(1);
        entityService.delete(2);
        entityService.delete(3);

        long size = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM entity", Long.class);
        Assert.assertEquals(0, size);
    }

    @Test
    public void shouldDeleteAllEntities_Test() {
        entityService.clear();

        long size = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM entity", Long.class);
        Assert.assertEquals(0, size);
    }
}
