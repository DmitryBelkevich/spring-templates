package com.hard.integrationTests.controllers;

import com.hard.config.AppConfig;
import com.hard.models.Entity;
import com.hard.utils.TestUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
@Sql(
        value = "classpath:sql_scripts/init.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(
        value = "classpath:sql_scripts/destroy.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
public class EntityControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void main_Test() throws Exception {
        mockMvc.perform(
                get("/api/entities")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        ;
    }

    /**
     * get all
     */

    @Test
    public void getAll_ShouldReturnEntities() throws Exception {
//        entityService.add(new Entity(1, "entity1"));
//        entityService.add(new Entity(2, "entity2"));
//        entityService.add(new Entity(3, "entity3"));

        mockMvc.perform(
                get("/api/entities")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.size()", is(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("entity1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("entity2")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("entity3")))
        ;
    }

    @Test
    @Ignore
    public void getAll_ShouldReturnStatusNoContent204() throws Exception {
        mockMvc.perform(
                get("/api/entities")
        )
                .andExpect(status().isNoContent())
        ;
    }

    /**
     * getById
     */

    @Test
    @Ignore
    public void getById_ShouldReturnEntity() throws Exception {
        // TODO - getById_ShouldReturnEntity

//        entityService.add(new Entity(1, "entity1"));

        mockMvc.perform(
                get("/api/entities/{id}", 1L)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("entity1")))
        ;
    }

    @Test
    @Ignore
    public void getById_ShouldReturnStatusNotFound404() throws Exception {
        // TODO - getById_ShouldReturnStatusNotFound404

        mockMvc.perform(
                get("/api/entities/{id}", 1L)
        )
                .andExpect(status().isNotFound())
        ;
    }

    /**
     * add
     */

    @Test
    @Ignore
    public void add_ShouldAddEntityAndReturnEntity() throws Exception {
        // TODO - add_ShouldAddEntityAndReturnEntity

        Entity entity = new Entity(1, "entity1");

        mockMvc.perform(
                post("/api/entities")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJson(entity))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("entity1")))
        ;
    }

    @Test
    @Ignore
    public void add_ShouldReturnStatusConflict409() throws Exception {
        // TODO - add_ShouldReturnStatusConflict409

        Entity entity = new Entity(1, "entity1");

//        entityService.add(entity);

        mockMvc.perform(
                post("/api/entities")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJson(entity))
        )
                .andExpect(status().isConflict())
        ;
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
    @Ignore
    public void update_ShouldUpdateEntityAndReturnEntity() throws Exception {
        // TODO - update_ShouldUpdateEntityAndReturnEntity

//        entityService.add(new Entity(1, "entity1"));
//        entityService.add(new Entity(2, ""));
//        entityService.add(new Entity(3, "entity3"));

        Entity entity = new Entity(2, "entity2");

        mockMvc.perform(
                put("/api/entities/{id}", 2L)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJson(entity))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("entity2")))
        ;
    }

    @Test
    @Ignore
    public void update_ShouldReturnStatusNotFound404() throws Exception {
        // TODO - update_ShouldReturnStatusNotFound404

//        entityService.add(new Entity(1, "entity1"));
//        entityService.add(new Entity(3, "entity3"));

        Entity entity = new Entity(2, "entity2");

        mockMvc.perform(
                put("/api/entities/{id}", 2L)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJson(entity))
        )
                .andExpect(status().isNotFound())
        ;
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
    @Ignore
    public void delete_ShouldDeleteEntityAndReturnStatusNoContent204() throws Exception {
        // TODO - delete_ShouldDeleteEntityAndReturnStatusNoContent204

//        entityService.add(new Entity(1, "entity1"));
//        entityService.add(new Entity(2, "entity2"));
//        entityService.add(new Entity(3, "entity3"));

        mockMvc.perform(
                delete("/api/entities/{id}", 1L)
        )
                .andExpect(status().isNoContent())
        ;
    }

    @Test
    @Ignore
    public void delete_ShouldReturnStatusNotFound404() throws Exception {
        // TODO - delete_ShouldReturnStatusNotFound404
        mockMvc.perform(
                delete("/api/entities/{id}", 1L)
        )
                .andExpect(status().isNotFound())
        ;
    }

    /**
     * clear
     */

    @Test
    public void clear_ShouldDeleteEntitiesAndReturnStatusNoContent204() throws Exception {
        mockMvc.perform(
                delete("/api/entities")
        )
                .andExpect(status().isNoContent())
        ;
    }
}
