package com.hard.integrationTests.config;

import com.hard.AppTest;
import com.hard.services.EntityService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

public class ServicesBeansTest extends AppTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void shouldReturnEntityServiceImplBean_Test() {
        EntityService entityService = null;

        try {
            entityService = (EntityService) webApplicationContext.getBean("entityServiceImpl");
        } catch (NoSuchBeanDefinitionException e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }
}
