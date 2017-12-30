package com.hard.integrationTests.config;

import com.hard.AppTest;
import com.hard.repositories.EntityRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

public class RepositoriesBeansTest extends AppTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void shouldReturnEntityRepositoryBean_Test() {
        EntityRepository entityRepository = null;

        try {
            entityRepository = (EntityRepository) webApplicationContext.getBean("entityRepository");
        } catch (NoSuchBeanDefinitionException e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }
}
