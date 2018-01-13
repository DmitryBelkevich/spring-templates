package com.hard.integrationTests.controllers;

import com.hard.config.AppConfig;
import com.hard.config.MvcConfig;
import com.hard.config.SecurityConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {
                AppConfig.class,
                MvcConfig.class,
                SecurityConfig.class,
        }
)
@WebAppConfiguration
public class MainControllerTest {
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
                get("/")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("main/main"))
                .andExpect(forwardedUrl("/WEB-INF/views/main/main.jsp"))
        ;
    }
}
