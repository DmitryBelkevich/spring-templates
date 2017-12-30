package com.hard.integrationTests.controllers;

import com.hard.AppTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EntityControllerTest extends AppTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

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

    /**
     * get all
     */

    @Test
    public void getAll_ShouldReturnEntities() throws Exception {
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
    public void getById_ShouldReturnEntity() throws Exception {
        mockMvc.perform(
                get("/api/entities/{id}", 1L)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("entity1")))
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
