package com.hard.unitTests.services;

import com.hard.config.AppConfig;
import com.hard.dao.EntityDao;
import com.hard.models.Entity;
import com.hard.services.impl.EntityServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {
                AppConfig.class,
        }
)
@WebAppConfiguration
public class EntityServiceTest {
    @Mock
    private EntityDao entityDaoMock;

    @InjectMocks
    private EntityServiceImpl entityService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAll_Test() {
        Mockito.when(
                entityDaoMock.getAll()
        ).thenReturn(
                Arrays.asList(
                        new Entity(1, "entity1"),
                        new Entity(2, "entity2"),
                        new Entity(3, "entity3")
                )
        );

        Collection<Entity> collection = entityService.getAll();
        List<Entity> entities = new ArrayList<>(collection);

        Assert.assertEquals(3, entities.size());

        Entity entity1 = entities.get(0);
        Assert.assertEquals(1, entity1.getId());
        Assert.assertEquals("entity1", entity1.getName());

        Entity entity2 = entities.get(1);
        Assert.assertEquals(2, entity2.getId());
        Assert.assertEquals("entity2", entity2.getName());

        Entity entity3 = entities.get(2);
        Assert.assertEquals(3, entity3.getId());
        Assert.assertEquals("entity3", entity3.getName());

        Mockito.verify(entityDaoMock, Mockito.times(1)).getAll();
        Mockito.verifyNoMoreInteractions(entityDaoMock);
    }

    @Test
    public void getById_Test() {
        Mockito.when(
                entityDaoMock.getById(1)
        ).thenReturn(
                new Entity(1, "entity1")
        );

        Mockito.when(
                entityDaoMock.getById(2)
        ).thenReturn(
                new Entity(2, "entity2")
        );

        Mockito.when(
                entityDaoMock.getById(3)
        ).thenReturn(
                new Entity(3, "entity3")
        );

        Entity entity1 = entityService.getById(1);
        Assert.assertEquals(1, entity1.getId());
        Assert.assertEquals("entity1", entity1.getName());

        Entity entity2 = entityService.getById(2);
        Assert.assertEquals(2, entity2.getId());
        Assert.assertEquals("entity2", entity2.getName());

        Entity entity3 = entityService.getById(3);
        Assert.assertEquals(3, entity3.getId());
        Assert.assertEquals("entity3", entity3.getName());

        Mockito.verify(entityDaoMock, Mockito.times(1)).getById(1);
        Mockito.verify(entityDaoMock, Mockito.times(1)).getById(2);
        Mockito.verify(entityDaoMock, Mockito.times(1)).getById(3);
        Mockito.verifyNoMoreInteractions(entityDaoMock);
    }

    @Test
    public void add_Test() {
        Mockito.when(
                entityDaoMock.getAll()
        ).thenReturn(
                Arrays.asList(
                        new Entity(1, "entity1"),
                        new Entity(2, "entity2"),
                        new Entity(3, "entity3")
                )
        );

        Entity newEntity = new Entity(4, "entity4");
        Mockito.doAnswer(invocationOnMock -> {
            Mockito.when(
                    entityDaoMock.getAll()
            ).thenReturn(
                    Arrays.asList(
                            new Entity(1, "entity1"),
                            new Entity(2, "entity2"),
                            new Entity(3, "entity3"),
                            newEntity
                    )
            );

            return null;
        }).when(entityDaoMock).add(newEntity);

        Collection<Entity> collection = entityService.getAll();
        List<Entity> entities = new ArrayList<>(collection);

        Assert.assertEquals(3, entities.size());

        Entity entity1 = entities.get(0);
        Assert.assertEquals(1, entity1.getId());
        Assert.assertEquals("entity1", entity1.getName());

        Entity entity2 = entities.get(1);
        Assert.assertEquals(2, entity2.getId());
        Assert.assertEquals("entity2", entity2.getName());

        Entity entity3 = entities.get(2);
        Assert.assertEquals(3, entity3.getId());
        Assert.assertEquals("entity3", entity3.getName());

        entityService.add(newEntity);

        collection = entityService.getAll();
        entities = new ArrayList<>(collection);

        Assert.assertEquals(4, entities.size());

        entity1 = entities.get(0);
        Assert.assertEquals(1, entity1.getId());
        Assert.assertEquals("entity1", entity1.getName());

        entity2 = entities.get(1);
        Assert.assertEquals(2, entity2.getId());
        Assert.assertEquals("entity2", entity2.getName());

        entity3 = entities.get(2);
        Assert.assertEquals(3, entity3.getId());
        Assert.assertEquals("entity3", entity3.getName());

        Entity entity4 = entities.get(3);
        Assert.assertEquals(4, entity4.getId());
        Assert.assertEquals("entity4", entity4.getName());

        Mockito.verify(entityDaoMock, Mockito.times(2)).getAll();
        Mockito.verify(entityDaoMock, Mockito.times(1)).add(newEntity);
        Mockito.verifyNoMoreInteractions(entityDaoMock);
    }

    // TODO - add all

    @Test
    public void update_Test() {
        Mockito.when(
                entityDaoMock.getAll()
        ).thenReturn(
                Arrays.asList(
                        new Entity(1, "entity1"),
                        new Entity(2, "entity2"),
                        new Entity(3, "entity3")
                )
        );

        Entity entity4 = new Entity(4, "entity4");
        Mockito.doAnswer(invocationOnMock -> {
            Mockito.when(
                    entityDaoMock.getAll()
            ).thenReturn(
                    Arrays.asList(
                            new Entity(1, "entity1"),
                            new Entity(3, "entity3"),
                            entity4
                    )
            );

            return null;
        }).when(entityDaoMock).update(2, entity4);

        Collection<Entity> collection = entityService.getAll();
        List<Entity> entities = new ArrayList<>(collection);

        Assert.assertEquals(3, entities.size());

        Entity entity1 = entities.get(0);
        Assert.assertEquals(1, entity1.getId());
        Assert.assertEquals("entity1", entity1.getName());

        Entity entity2 = entities.get(1);
        Assert.assertEquals(2, entity2.getId());
        Assert.assertEquals("entity2", entity2.getName());

        Entity entity3 = entities.get(2);
        Assert.assertEquals(3, entity3.getId());
        Assert.assertEquals("entity3", entity3.getName());

        entityService.update(2, entity4);

        collection = entityService.getAll();
        entities = new ArrayList<>(collection);

        Assert.assertEquals(3, entities.size());

        entity1 = entities.get(0);
        Assert.assertEquals(1, entity1.getId());
        Assert.assertEquals("entity1", entity1.getName());

        entity2 = entities.get(1);
        Assert.assertEquals(3, entity2.getId());
        Assert.assertEquals("entity3", entity2.getName());

        entity3 = entities.get(2);
        Assert.assertEquals(4, entity3.getId());
        Assert.assertEquals("entity4", entity3.getName());

        Mockito.verify(entityDaoMock, Mockito.times(2)).getAll();
        Mockito.verify(entityDaoMock, Mockito.times(1)).update(2, entity4);
        Mockito.verifyNoMoreInteractions(entityDaoMock);
    }

    // TODO - update all

    @Test
    public void delete_Test() {
        Mockito.when(
                entityDaoMock.getAll()
        ).thenReturn(
                Arrays.asList(
                        new Entity(1, "entity1"),
                        new Entity(2, "entity2"),
                        new Entity(3, "entity3")
                )
        );

        Mockito.doAnswer(invocationOnMock -> {
            Mockito.when(
                    entityDaoMock.getAll()
            ).thenReturn(
                    Arrays.asList(
                            new Entity(1, "entity1"),
                            new Entity(3, "entity3")
                    )
            );

            return null;
        }).when(entityDaoMock).delete(2);

        Collection<Entity> collection = entityService.getAll();
        List<Entity> entities = new ArrayList<>(collection);

        Assert.assertEquals(3, entities.size());

        Entity entity1 = entities.get(0);
        Assert.assertEquals(1, entity1.getId());
        Assert.assertEquals("entity1", entity1.getName());

        Entity entity2 = entities.get(1);
        Assert.assertEquals(2, entity2.getId());
        Assert.assertEquals("entity2", entity2.getName());

        Entity entity3 = entities.get(2);
        Assert.assertEquals(3, entity3.getId());
        Assert.assertEquals("entity3", entity3.getName());

        entityService.delete(2);

        collection = entityService.getAll();
        entities = new ArrayList<>(collection);

        Assert.assertEquals(2, entities.size());

        entity1 = entities.get(0);
        Assert.assertEquals(1, entity1.getId());
        Assert.assertEquals("entity1", entity1.getName());

        entity3 = entities.get(1);
        Assert.assertEquals(3, entity3.getId());
        Assert.assertEquals("entity3", entity3.getName());

        Mockito.verify(entityDaoMock, Mockito.times(2)).getAll();
        Mockito.verify(entityDaoMock, Mockito.times(1)).delete(2);
        Mockito.verifyNoMoreInteractions(entityDaoMock);
    }

    @Test
    public void clear_Test() {
        Mockito.when(
                entityDaoMock.getAll()
        ).thenReturn(
                Arrays.asList(
                        new Entity(1, "entity1"),
                        new Entity(2, "entity2"),
                        new Entity(3, "entity3")
                )
        );

        Mockito.doAnswer(invocationOnMock -> {
            Mockito.when(
                    entityDaoMock.getAll()
            ).thenReturn(
                    Arrays.asList()
            );

            return null;
        }).when(entityDaoMock).clear();

        Collection<Entity> collection = entityService.getAll();
        List<Entity> entities = new ArrayList<>(collection);

        Assert.assertEquals(3, entities.size());

        Entity entity1 = entities.get(0);
        Assert.assertEquals(1, entity1.getId());
        Assert.assertEquals("entity1", entity1.getName());

        Entity entity2 = entities.get(1);
        Assert.assertEquals(2, entity2.getId());
        Assert.assertEquals("entity2", entity2.getName());

        Entity entity3 = entities.get(2);
        Assert.assertEquals(3, entity3.getId());
        Assert.assertEquals("entity3", entity3.getName());

        entityService.clear();

        collection = entityService.getAll();
        entities = new ArrayList<>(collection);

        Assert.assertEquals(0, entities.size());

        Mockito.verify(entityDaoMock, Mockito.times(2)).getAll();
        Mockito.verify(entityDaoMock, Mockito.times(1)).clear();
        Mockito.verifyNoMoreInteractions(entityDaoMock);
    }
}
