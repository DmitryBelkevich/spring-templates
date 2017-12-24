package com.hard.unitTests.controllers;

import com.hard.config.AppConfig;
import com.hard.controllers.EntityController;
import com.hard.models.Entity;
import com.hard.services.EntityService;
import com.hard.utils.TestUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {
                AppConfig.class,
        }
)
@WebAppConfiguration
public class EntityControllerTest {
    @Mock
    private EntityService entityServiceMock;

    @InjectMocks
    private EntityController entityController;

    private MockMvc mockMvc;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(entityController).build();
    }

    /**
     * get all
     */

    @Test
    public void getAll_ShouldReturnEntities() throws Exception {
        Mockito.when(
                entityServiceMock.getAll()
        )
                .thenReturn(
                        Arrays.asList(
                                new Entity(1, "entity1"),
                                new Entity(2, "entity2"),
                                new Entity(3, "entity3")
                        )
                );

        mockMvc.perform(
                get("/api/entities")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.size()", is(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("entity1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("entity2")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("entity3")))
        ;

        Mockito.verify(entityServiceMock, Mockito.times(1)).getAll();
        Mockito.verifyNoMoreInteractions(entityServiceMock);
    }

    @Test
    public void getAll_ShouldReturnStatusNoContent204() throws Exception {
        Mockito.when(
                entityServiceMock.getAll()
        ).thenReturn(
                Arrays.asList()
        );

        mockMvc.perform(
                get("/api/entities")
        )
                .andExpect(status().isNoContent());
    }

    /**
     * getById
     */

    @Test
    public void getById_ShouldReturnEntity() throws Exception {
        Mockito.when(
                entityServiceMock.getById(1)
        ).thenReturn(
                new Entity(1, "entity1")
        );

        Mockito.when(
                entityServiceMock.getById(2)
        ).thenReturn(
                new Entity(2, "entity2")
        );

        Mockito.when(
                entityServiceMock.getById(3)
        ).thenReturn(
                new Entity(3, "entity3")
        );

        mockMvc.perform(
                get("/api/entities/{id}", 1L)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("entity1")))
        ;

        mockMvc.perform(
                get("/api/entities/{id}", 2L)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("entity2")))
        ;

        mockMvc.perform(
                get("/api/entities/{id}", 3L)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.name", is("entity3")))
        ;

        Mockito.verify(entityServiceMock, Mockito.times(1)).getById(1);
        Mockito.verify(entityServiceMock, Mockito.times(1)).getById(2);
        Mockito.verify(entityServiceMock, Mockito.times(1)).getById(3);
        Mockito.verifyNoMoreInteractions(entityServiceMock);
    }

    @Test
    public void getById_ShouldReturnStatusNotFound404() throws Exception {
        Mockito.when(
                entityServiceMock.getById(1)
        ).thenReturn(null);

        mockMvc.perform(
                get("/api/entities/{id}", 1L)
        )
                .andExpect(status().isNotFound())
        ;

        Mockito.verify(entityServiceMock, Mockito.times(1)).getById(1);
        Mockito.verifyNoMoreInteractions(entityServiceMock);
    }

    /**
     * add
     */

    @Test
    public void add_ShouldAddEntityAndReturnEntity() throws Exception {
        Mockito.when(
                entityServiceMock.getAll()
        ).thenReturn(
                Arrays.asList(
                        new Entity(1, "entity1"),
                        new Entity(2, "entity2"),
                        new Entity(3, "entity3")
                )
        );

        Mockito.when(
                entityServiceMock.getById(1)
        ).thenReturn(new Entity(1, "entity1"));

        Mockito.when(
                entityServiceMock.getById(2)
        ).thenReturn(new Entity(2, "entity2"));

        Mockito.when(
                entityServiceMock.getById(3)
        ).thenReturn(new Entity(3, "entity3"));

        Mockito.doAnswer(invocationOnMock -> {
            Mockito.when(
                    entityServiceMock.getAll()
            ).thenReturn(
                    Arrays.asList(
                            new Entity(1, "entity1"),
                            new Entity(2, "entity2"),
                            new Entity(3, "entity3"),
                            invocationOnMock.getArgument(0)
                    )
            );

            Mockito.when(
                    entityServiceMock.getById(4)
            ).thenReturn(new Entity(4, "entity4"));

            return null;
        }).when(entityServiceMock).add(Mockito.refEq(new Entity(4, "entity4")));

        mockMvc.perform(
                get("/api/entities")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.size()", is(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("entity1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("entity2")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("entity3")))
        ;

        mockMvc.perform(
                post("/api/entities")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJson(new Entity(4, "entity4")))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.name", is("entity4")))
        ;

        mockMvc.perform(
                get("/api/entities")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.size()", is(4)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("entity1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("entity2")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("entity3")))
                .andExpect(jsonPath("$[3].id", is(4)))
                .andExpect(jsonPath("$[3].name", is("entity4")))
        ;

        Mockito.verify(entityServiceMock, Mockito.times(2)).getAll();
        Mockito.verify(entityServiceMock, Mockito.times(1)).getById(4);
        Mockito.verify(entityServiceMock, Mockito.times(1)).add(Mockito.refEq(new Entity(4, "entity4")));
        Mockito.verifyNoMoreInteractions(entityServiceMock);
    }

    @Test
    public void add_ShouldReturnStatusConflict409() throws Exception {
        Mockito.when(
                entityServiceMock.getAll()
        ).thenReturn(
                Arrays.asList(
                        new Entity(1, "entity1"),
                        new Entity(2, "entity2"),
                        new Entity(3, "entity3")
                )
        );

        Mockito.when(
                entityServiceMock.getById(1)
        ).thenReturn(new Entity(1, "entity1"));

        Mockito.when(
                entityServiceMock.getById(2)
        ).thenReturn(new Entity(2, "entity2"));

        Mockito.when(
                entityServiceMock.getById(3)
        ).thenReturn(new Entity(3, "entity3"));

        mockMvc.perform(
                get("/api/entities")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.size()", is(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("entity1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("entity2")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("entity3")))
        ;

        mockMvc.perform(
                post("/api/entities")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJson(new Entity(1, "entity1")))
        )
                .andExpect(status().isConflict())
        ;

        mockMvc.perform(
                get("/api/entities")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.size()", is(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("entity1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("entity2")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("entity3")))
        ;

        Mockito.verify(entityServiceMock, Mockito.times(2)).getAll();
        Mockito.verify(entityServiceMock, Mockito.times(1)).getById(1);
        Mockito.verifyNoMoreInteractions(entityServiceMock);
    }

    /**
     * add all
     */

    @Test
    @Ignore
    public void addCollection_ShouldAddEntitiesAndReturnEntities() throws Exception {
        // TODO - addCollection_ShouldAddEntitiesAndReturnEntities
    }

    @Test
    @Ignore
    public void addCollection_ShouldReturnStatusConflict409() throws Exception {
        // TODO - addCollection_ShouldReturnStatusConflict409
    }

    /**
     * update
     */

    @Test
    public void update_ShouldUpdateEntityAndReturnEntity() throws Exception {
        Mockito.when(
                entityServiceMock.getAll()
        ).thenReturn(
                Arrays.asList(
                        new Entity(1, "entity1"),
                        new Entity(2, "entity2"),
                        new Entity(3, "entity3")
                )
        );

        Mockito.when(
                entityServiceMock.getById(1)
        ).thenReturn(new Entity(1, "entity1"));

        Mockito.when(
                entityServiceMock.getById(2)
        ).thenReturn(new Entity(2, "entity2"));

        Mockito.when(
                entityServiceMock.getById(3)
        ).thenReturn(new Entity(3, "entity3"));

        Mockito.doAnswer(invocationOnMock -> {
            Mockito.when(
                    entityServiceMock.getAll()
            ).thenReturn(
                    Arrays.asList(
                            new Entity(1, "entity1"),
                            new Entity(3, "entity3"),
                            invocationOnMock.getArgument(1)
                    )
            );

            Mockito.when(
                    entityServiceMock.getById(2)
            ).thenReturn(null);

            Mockito.when(
                    entityServiceMock.getById(4)
            ).thenReturn(new Entity(4, "entity4"));

            return null;
        }).when(entityServiceMock).update(Mockito.eq(2L), Mockito.refEq(new Entity(4, "entity4")));

        mockMvc.perform(
                get("/api/entities")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.size()", is(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("entity1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("entity2")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("entity3")))
        ;

        mockMvc.perform(
                put("/api/entities/{id}", 2L)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJson(new Entity(4, "entity4")))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.name", is("entity4")))
        ;

        mockMvc.perform(
                get("/api/entities")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.size()", is(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("entity1")))
                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[1].name", is("entity3")))
                .andExpect(jsonPath("$[2].id", is(4)))
                .andExpect(jsonPath("$[2].name", is("entity4")))
        ;

        Mockito.verify(entityServiceMock, Mockito.times(2)).getAll();
        Mockito.verify(entityServiceMock, Mockito.times(1)).getById(2);
        Mockito.verify(entityServiceMock, Mockito.times(1)).update(Mockito.eq(2L), Mockito.refEq(new Entity(4, "entity4")));
        Mockito.verifyNoMoreInteractions(entityServiceMock);
    }

    @Test
    public void update_ShouldReturnStatusNotFound404() throws Exception {
        Mockito.when(
                entityServiceMock.getAll()
        ).thenReturn(
                Arrays.asList(
                        new Entity(1, "entity1"),
                        new Entity(3, "entity3")
                )
        );

        Mockito.when(
                entityServiceMock.getById(1)
        ).thenReturn(new Entity(1, "entity1"));

        Mockito.when(
                entityServiceMock.getById(3)
        ).thenReturn(new Entity(3, "entity3"));

        mockMvc.perform(
                put("/api/entities/{id}", 2L)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJson(new Entity(2, "entity2")))
        )
                .andExpect(status().isNotFound())
        ;

        Mockito.verify(entityServiceMock, Mockito.times(1)).getById(2);
        Mockito.verifyNoMoreInteractions(entityServiceMock);
    }

    /**
     * update all
     */

    @Test
    @Ignore
    public void updateCollection_ShouldUpdateEntitiesAndReturnEntities() throws Exception {
        // TODO - updateCollection_ShouldUpdateEntitiesAndReturnEntities
    }

    @Test
    @Ignore
    public void updateCollection_ShouldReturnStatusNotFound404() throws Exception {
        // TODO - updateCollection_ShouldReturnStatusNotFound404
    }

    /**
     * delete
     */

    @Test
    public void delete_ShouldDeleteEntityAndReturnStatusNoContent204() throws Exception {
        Mockito.when(
                entityServiceMock.getAll()
        ).thenReturn(
                Arrays.asList(
                        new Entity(1, "entity1"),
                        new Entity(2, "entity2"),
                        new Entity(3, "entity3")
                )
        );

        Mockito.when(
                entityServiceMock.getById(1)
        ).thenReturn(new Entity(1, "entity1"));

        Mockito.when(
                entityServiceMock.getById(2)
        ).thenReturn(new Entity(2, "entity2"));

        Mockito.when(
                entityServiceMock.getById(3)
        ).thenReturn(new Entity(3, "entity3"));

        Mockito.doAnswer(invocationOnMock -> {
            Mockito.when(
                    entityServiceMock.getAll()
            ).thenReturn(
                    Arrays.asList(
                            new Entity(1, "entity1"),
                            new Entity(3, "entity3")
                    )
            );

            Mockito.when(
                    entityServiceMock.getById(2)
            ).thenReturn(null);

            return null;
        }).when(entityServiceMock).delete(2);

        mockMvc.perform(
                get("/api/entities")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.size()", is(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("entity1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("entity2")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("entity3")))
        ;

        mockMvc.perform(
                delete("/api/entities/{id}", 2L)
        )
                .andExpect(status().isNoContent())
        ;

        mockMvc.perform(
                get("/api/entities")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("entity1")))
                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[1].name", is("entity3")))
        ;

        Mockito.verify(entityServiceMock, Mockito.times(2)).getAll();
        Mockito.verify(entityServiceMock, Mockito.times(1)).getById(2);
        Mockito.verify(entityServiceMock, Mockito.times(1)).delete(2);
        Mockito.verifyNoMoreInteractions(entityServiceMock);
    }

    @Test
    public void delete_ShouldReturnStatusNotFound404() throws Exception {
        mockMvc.perform(
                delete("/api/entities/{id}", 1L)
        )
                .andExpect(status().isNotFound())
        ;

        Mockito.verify(entityServiceMock, Mockito.times(1)).getById(1);
        Mockito.verifyNoMoreInteractions(entityServiceMock);
    }

    /**
     * clear
     */

    @Test
    public void clear_ShouldDeleteEntitiesAndReturnStatusNoContent204() throws Exception {
        Mockito.when(
                entityServiceMock.getAll()
        ).thenReturn(
                Arrays.asList(
                        new Entity(1, "entity1"),
                        new Entity(2, "entity2"),
                        new Entity(3, "entity3")
                )
        );

        Mockito.doAnswer(invocationOnMock -> {
            Mockito.when(
                    entityServiceMock.getAll()
            ).thenReturn(
                    Arrays.asList()
            );

            return null;
        }).when(entityServiceMock).clear();

        mockMvc.perform(
                get("/api/entities")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.size()", is(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("entity1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("entity2")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("entity3")))
        ;

        mockMvc.perform(
                delete("/api/entities")
        )
                .andExpect(status().isNoContent())
        ;

        mockMvc.perform(
                get("/api/entities")
        )
                .andExpect(status().isNoContent());

        Mockito.verify(entityServiceMock, Mockito.times(2)).getAll();
        Mockito.verify(entityServiceMock, Mockito.times(1)).clear();
        Mockito.verifyNoMoreInteractions(entityServiceMock);
    }
}
