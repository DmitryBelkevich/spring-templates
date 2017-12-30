package com.hard.integrationTests.service;

import com.hard.models.Entity;
import com.hard.services.EntityService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EntityServiceTest {
    @Autowired
    private EntityService entityService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Entity> getAll() {
        return jdbcTemplate.query(
                "SELECT * FROM entity",
                (ResultSet resultSet, int numRow) -> {
                    Entity entity = new Entity();

                    entity.setId(resultSet.getLong("id"));
                    entity.setName(resultSet.getString("name"));

                    return entity;
                }
        );
    }

    @Before
    public void init() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS entity");
        jdbcTemplate.execute("CREATE TABLE entity(" +
                "id BIGSERIAL NOT NULL PRIMARY KEY ," +
                "name VARCHAR(256)" +
                ")");

        jdbcTemplate.execute("INSERT INTO entity (name) VALUES ('entity1')");
        jdbcTemplate.execute("INSERT INTO entity (name) VALUES ('entity2')");
        jdbcTemplate.execute("INSERT INTO entity (name) VALUES ('entity3')");
    }

    @After
    public void destroy() {
        jdbcTemplate.execute("DROP TABLE entity");
    }

    @Test
    public void findAll_shouldReturnAllEntities() {
        List<Entity> entities = new ArrayList<>(entityService.getAll());

        Assert.assertEquals(3, entities.size());

        Assert.assertEquals(1, entities.get(0).getId());
        Assert.assertEquals("entity1", entities.get(0).getName());

        Assert.assertEquals(2, entities.get(1).getId());
        Assert.assertEquals("entity2", entities.get(1).getName());

        Assert.assertEquals(3, entities.get(2).getId());
        Assert.assertEquals("entity3", entities.get(2).getName());
    }

    @Test
    public void getById_shouldReturnEntity() {
        Entity e1 = entityService.getById(1);
        Entity e2 = entityService.getById(2L);
        Entity e3 = entityService.getById(3L);

        Assert.assertEquals(1, e1.getId());
        Assert.assertEquals("entity1", e1.getName());

        Assert.assertEquals(2, e2.getId());
        Assert.assertEquals("entity2", e2.getName());

        Assert.assertEquals(3, e3.getId());
        Assert.assertEquals("entity3", e3.getName());
    }

    @Test
    public void getByName_shouldReturnEntityByName() {
        Entity e1 = entityService.getByName("entity1");
        Entity e2 = entityService.getByName("entity2");
        Entity e3 = entityService.getByName("entity3");

        Assert.assertEquals(1, e1.getId());
        Assert.assertEquals("entity1", e1.getName());

        Assert.assertEquals(2, e2.getId());
        Assert.assertEquals("entity2", e2.getName());

        Assert.assertEquals(3, e3.getId());
        Assert.assertEquals("entity3", e3.getName());
    }

    @Test
    public void save_shouldAddEntity() {
        entityService.save(new Entity("entity4"));
        entityService.save(new Entity("entity5"));
        entityService.save(new Entity("entity6"));

        List<Entity> entities = getAll();

        Assert.assertEquals(6, entities.size());

        Assert.assertEquals(1, entities.get(0).getId());
        Assert.assertEquals("entity1", entities.get(0).getName());

        Assert.assertEquals(2, entities.get(1).getId());
        Assert.assertEquals("entity2", entities.get(1).getName());

        Assert.assertEquals(3, entities.get(2).getId());
        Assert.assertEquals("entity3", entities.get(2).getName());

        Assert.assertEquals(4, entities.get(3).getId());
        Assert.assertEquals("entity4", entities.get(3).getName());

        Assert.assertEquals(5, entities.get(4).getId());
        Assert.assertEquals("entity5", entities.get(4).getName());

        Assert.assertEquals(6, entities.get(5).getId());
        Assert.assertEquals("entity6", entities.get(5).getName());
    }

    @Test
    public void save_shouldUpdateEntity() {
        entityService.save(new Entity(1, "entity4"));
        entityService.save(new Entity(2, "entity5"));
        entityService.save(new Entity(3, "entity6"));

        List<Entity> entities = getAll();

        Assert.assertEquals(3, entities.size());

        Assert.assertEquals(1, entities.get(0).getId());
        Assert.assertEquals("entity4", entities.get(0).getName());

        Assert.assertEquals(2, entities.get(1).getId());
        Assert.assertEquals("entity5", entities.get(1).getName());

        Assert.assertEquals(3, entities.get(2).getId());
        Assert.assertEquals("entity6", entities.get(2).getName());
    }

    @Test
    public void delete_shouldDeleteEntity() {
        entityService.delete(1);
        entityService.delete(2);
        entityService.delete(3);

        List<Entity> entities = getAll();

        Assert.assertEquals(0, entities.size());
    }

    @Test
    public void deleteAll_shouldDeleteAllEntities() {
        entityService.deleteAll();

        List<Entity> entities = getAll();

        Assert.assertEquals(0, entities.size());
    }
}
