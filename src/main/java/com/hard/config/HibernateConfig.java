package com.hard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

@Configuration
@PropertySource("classpath:hibernate.properties")
public class HibernateConfig {
    @Autowired
    private Environment environment;

    @Autowired
    private DataSource dataSource;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        sessionFactory.setPackagesToScan(environment.getProperty("db.entity.package"));
        sessionFactory.setDataSource(dataSource);

        Properties properties = new Properties();

        // Setting JDBC properties
//        properties.setProperty(DRIVER, environment.getProperty("db.driver"));
//        properties.setProperty(URL, environment.getProperty("db.url"));
//        properties.setProperty(USER, environment.getProperty("db.user"));
//        properties.setProperty(PASS, environment.getProperty("db.password"));

        // Setting Hibernate properties
        properties.setProperty(DIALECT, environment.getProperty("hibernate.dialect"));
        properties.setProperty(SHOW_SQL, environment.getProperty("hibernate.show_sql"));
        properties.setProperty(HBM2DDL_AUTO, environment.getProperty("hibernate.hbm2ddl.auto"));
        properties.setProperty(ENABLE_LAZY_LOAD_NO_TRANS, environment.getProperty("hibernate.enable_lazy_load_no_trans"));
        properties.setProperty(CURRENT_SESSION_CONTEXT_CLASS, environment.getProperty("hibernate.current_session_context_class"));

        // Setting C3P0 properties
        properties.setProperty(C3P0_MIN_SIZE, environment.getProperty("hibernate.c3p0.min_size"));
        properties.setProperty(C3P0_MAX_SIZE, environment.getProperty("hibernate.c3p0.max_size"));
        properties.setProperty(C3P0_ACQUIRE_INCREMENT, environment.getProperty("hibernate.c3p0.acquire_increment"));
        properties.setProperty(C3P0_TIMEOUT, environment.getProperty("hibernate.c3p0.timeout"));
        properties.setProperty(C3P0_MAX_STATEMENTS, environment.getProperty("hibernate.c3p0.max_statements"));
        properties.setProperty(C3P0_IDLE_TEST_PERIOD, environment.getProperty("hibernate.c3p0.idle_test_period"));

        sessionFactory.setHibernateProperties(properties);

        return sessionFactory;
    }
}
