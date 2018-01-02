package com.hard.config.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

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

    public Properties getHibernateProperties() {
        Properties hibernateProperties = new Properties();

        // Setting JDBC properties
//        hibernateProperties.setProperty(DRIVER, environment.getProperty("db.driver"));
//        hibernateProperties.setProperty(URL, environment.getProperty("db.url"));
//        hibernateProperties.setProperty(USER, environment.getProperty("db.user"));
//        hibernateProperties.setProperty(PASS, environment.getProperty("db.password"));

        // Setting Hibernate properties
        hibernateProperties.setProperty(DIALECT, environment.getProperty("hibernate.dialect"));
        hibernateProperties.setProperty(SHOW_SQL, environment.getProperty("hibernate.show_sql"));
        hibernateProperties.setProperty(HBM2DDL_AUTO, environment.getProperty("hibernate.hbm2ddl.auto"));
        hibernateProperties.setProperty(ENABLE_LAZY_LOAD_NO_TRANS, environment.getProperty("hibernate.enable_lazy_load_no_trans"));
        hibernateProperties.setProperty(CURRENT_SESSION_CONTEXT_CLASS, environment.getProperty("hibernate.current_session_context_class"));

        // Setting C3P0 properties
        hibernateProperties.setProperty(C3P0_MIN_SIZE, environment.getProperty("hibernate.c3p0.min_size"));
        hibernateProperties.setProperty(C3P0_MAX_SIZE, environment.getProperty("hibernate.c3p0.max_size"));
        hibernateProperties.setProperty(C3P0_ACQUIRE_INCREMENT, environment.getProperty("hibernate.c3p0.acquire_increment"));
        hibernateProperties.setProperty(C3P0_TIMEOUT, environment.getProperty("hibernate.c3p0.timeout"));
        hibernateProperties.setProperty(C3P0_MAX_STATEMENTS, environment.getProperty("hibernate.c3p0.max_statements"));
        hibernateProperties.setProperty(C3P0_IDLE_TEST_PERIOD, environment.getProperty("hibernate.c3p0.idle_test_period"));

        return hibernateProperties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setPackagesToScan(environment.getRequiredProperty("db.entity.package"));
        entityManagerFactory.setDataSource(dataSource);

        Properties hibernateProperties = this.getHibernateProperties();
        entityManagerFactory.setJpaProperties(hibernateProperties);

        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return entityManagerFactory;
    }
}
