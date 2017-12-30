package com.hard.integrationTests.repositories;

import com.hard.models.Entity;
import com.hard.repositories.EntityRepository;
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
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EntityRepositoryTest {
    @Autowired
    private EntityRepository entityRepository;

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
        List<Entity> entities = entityRepository.findAll();

        Assert.assertEquals(3, entities.size());

        Assert.assertEquals(1, entities.get(0).getId());
        Assert.assertEquals("entity1", entities.get(0).getName());

        Assert.assertEquals(2, entities.get(1).getId());
        Assert.assertEquals("entity2", entities.get(1).getName());

        Assert.assertEquals(3, entities.get(2).getId());
        Assert.assertEquals("entity3", entities.get(2).getName());
    }

    @Test
    public void findOne_shouldReturnEntity() {
        Entity e1 = entityRepository.findOne(1L);
        Entity e2 = entityRepository.findOne(2L);
        Entity e3 = entityRepository.findOne(3L);

        Assert.assertEquals(1, e1.getId());
        Assert.assertEquals("entity1", e1.getName());

        Assert.assertEquals(2, e2.getId());
        Assert.assertEquals("entity2", e2.getName());

        Assert.assertEquals(3, e3.getId());
        Assert.assertEquals("entity3", e3.getName());
    }

    @Test
    public void findByName_shouldReturnEntity() {
        Entity e1 = entityRepository.findByName("entity1");
        Entity e2 = entityRepository.findByName("entity2");
        Entity e3 = entityRepository.findByName("entity3");

        Assert.assertEquals(1, e1.getId());
        Assert.assertEquals("entity1", e1.getName());

        Assert.assertEquals(2, e2.getId());
        Assert.assertEquals("entity2", e2.getName());

        Assert.assertEquals(3, e3.getId());
        Assert.assertEquals("entity3", e3.getName());
    }

    @Test
    public void save_shouldAddEntity() {
        entityRepository.save(new Entity("entity4"));
        entityRepository.save(new Entity("entity5"));
        entityRepository.save(new Entity("entity6"));

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
    public void saveAndFlush_shouldUpdateEntity() {
        entityRepository.saveAndFlush(new Entity(1, "entity4"));
        entityRepository.saveAndFlush(new Entity(2, "entity5"));
        entityRepository.saveAndFlush(new Entity(3, "entity6"));

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
        entityRepository.delete(1L);
        entityRepository.delete(2L);
        entityRepository.delete(3L);

        List<Entity> entities = getAll();

        Assert.assertEquals(0, entities.size());
    }

    @Test
    public void deleteAll_shouldDeleteAllEntities() {
        entityRepository.deleteAll();

        List<Entity> entities = getAll();

        Assert.assertEquals(0, entities.size());
    }
}
