package com.hard.integrationTests.config;

import com.hard.AppTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

public class ConfigBeansTest extends AppTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private Environment environment;

    @Test
    public void shouldReturnDataSourceBean_Test() {
        DataSource dataSource = null;

        try {
            dataSource = (DataSource) webApplicationContext.getBean("dataSource");
        } catch (NoSuchBeanDefinitionException e) {
            Assert.fail(e.getLocalizedMessage());
        }

        DriverManagerDataSource driverManagerDataSource = (DriverManagerDataSource) dataSource;

        Assert.assertEquals(environment.getProperty("db.url"), driverManagerDataSource.getUrl());
        Assert.assertEquals(environment.getProperty("db.user"), driverManagerDataSource.getUsername());
        Assert.assertEquals(environment.getProperty("db.password"), driverManagerDataSource.getPassword());
    }

    @Test
    public void shouldReturnJdbcTemplateBean_Test() {
        JdbcTemplate jdbcTemplate = null;

        try {
            jdbcTemplate = (JdbcTemplate) webApplicationContext.getBean("jdbcTemplate");
        } catch (NoSuchBeanDefinitionException e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }

    @Test
    public void shouldReturnEntityManagerFactoryBean_Test() {
        EntityManagerFactory entityManagerFactory = null;

        try {
            entityManagerFactory = (EntityManagerFactory) webApplicationContext.getBean("entityManagerFactory");
        } catch (NoSuchBeanDefinitionException e) {
            Assert.fail(e.getLocalizedMessage());
        }

        Assert.assertEquals(environment.getProperty("db.entity.package"), entityManagerFactory.getProperties().get("db.entity.package"));
        Assert.assertEquals(environment.getProperty("hibernate.dialect"), entityManagerFactory.getProperties().get("hibernate.dialect"));
        Assert.assertEquals(environment.getProperty("hibernate.show_sql"), entityManagerFactory.getProperties().get("hibernate.show_sql"));
        Assert.assertEquals(environment.getProperty("hibernate.hbm2ddl.auto"), entityManagerFactory.getProperties().get("hibernate.hbm2ddl.auto"));
        Assert.assertEquals(environment.getProperty("hibernate.enable_lazy_load_no_trans"), entityManagerFactory.getProperties().get("hibernate.enable_lazy_load_no_trans"));
        Assert.assertEquals(environment.getProperty("hibernate.current_session_context_class"), entityManagerFactory.getProperties().get("hibernate.current_session_context_class"));
        Assert.assertEquals(environment.getProperty("hibernate.c3p0.min_size"), entityManagerFactory.getProperties().get("hibernate.c3p0.min_size"));
        Assert.assertEquals(environment.getProperty("hibernate.c3p0.max_size"), entityManagerFactory.getProperties().get("hibernate.c3p0.max_size"));
        Assert.assertEquals(environment.getProperty("hibernate.c3p0.timeout"), entityManagerFactory.getProperties().get("hibernate.c3p0.timeout"));
        Assert.assertEquals(environment.getProperty("hibernate.c3p0.max_statements"), entityManagerFactory.getProperties().get("hibernate.c3p0.max_statements"));
        Assert.assertEquals(environment.getProperty("hibernate.c3p0.acquire_increment"), entityManagerFactory.getProperties().get("hibernate.c3p0.acquire_increment"));
        Assert.assertEquals(environment.getProperty("hibernate.c3p0.idle_test_period"), entityManagerFactory.getProperties().get("hibernate.c3p0.idle_test_period"));
    }

    @Test
    public void shouldReturnJpaTransactionManagerBean_Test() {
        PlatformTransactionManager transactionManager = null;

        try {
            transactionManager = (PlatformTransactionManager) webApplicationContext.getBean("transactionManager");
        } catch (NoSuchBeanDefinitionException e) {
            Assert.fail(e.getLocalizedMessage());
        }

        Assert.assertEquals(webApplicationContext.getBean("dataSource"), ((JpaTransactionManager) transactionManager).getDataSource());
    }
}
