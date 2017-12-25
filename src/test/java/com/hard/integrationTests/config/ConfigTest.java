package com.hard.integrationTests.config;

import com.hard.config.AppConfig;
import com.hard.dao.EntityDao;
import com.hard.dao.FileEntityDao;
import com.hard.services.EntityService;
import com.hard.services.FileEntityService;
import com.hard.services.FileManagerService;
import com.hard.services.FileService;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {
                AppConfig.class,
        }
)
@WebAppConfiguration
public class ConfigTest {
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
    public void shouldReturnSessionFactoryBean_Test() {
        SessionFactory sessionFactory = null;

        try {
            sessionFactory = (SessionFactory) webApplicationContext.getBean("sessionFactory");
        } catch (NoSuchBeanDefinitionException e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }

    @Test
    public void shouldReturnTransactionManagerBean_Test() {
        HibernateTransactionManager transactionManager = null;

        try {
            transactionManager = (HibernateTransactionManager) webApplicationContext.getBean("transactionManager");
        } catch (NoSuchBeanDefinitionException e) {
            Assert.fail(e.getLocalizedMessage());
        }
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

    /**
     * Dao beans
     */

    @Test
    public void shouldReturnEntityDaoImplBean_Test() {
        EntityDao entityDao = null;

        try {
            entityDao = (EntityDao) webApplicationContext.getBean("entityDaoImpl");
        } catch (NoSuchBeanDefinitionException e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }

    @Test
    public void shouldReturnFileEntityDaoImplBean_Test() {
        FileEntityDao fileEntityDao = null;

        try {
            fileEntityDao = (FileEntityDao) webApplicationContext.getBean("fileEntityDaoImpl");
        } catch (NoSuchBeanDefinitionException e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }

    /**
     * Services beans
     */

    @Test
    public void shouldReturnEntityServiceImplBean_Test() {
        EntityService entityService = null;

        try {
            entityService = (EntityService) webApplicationContext.getBean("entityServiceImpl");
        } catch (NoSuchBeanDefinitionException e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }

    @Test
    public void shouldReturnFileEntityServiceImplBean_Test() {
        FileEntityService fileEntityService = null;

        try {
            fileEntityService = (FileEntityService) webApplicationContext.getBean("fileEntityServiceImpl");
        } catch (NoSuchBeanDefinitionException e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }

    @Test
    public void shouldReturnFileManagerServiceImplBean_Test() {
        FileManagerService fileManagerService = null;

        try {
            fileManagerService = (FileManagerService) webApplicationContext.getBean("fileManagerServiceImpl");
        } catch (NoSuchBeanDefinitionException e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }

    @Test
    public void shouldReturnFileServiceImplBean_Test() {
        FileService fileService = null;

        try {
            fileService = (FileService) webApplicationContext.getBean("fileServiceImpl");
        } catch (NoSuchBeanDefinitionException e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }
}
