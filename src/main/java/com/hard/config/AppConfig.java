package com.hard.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({
        "com.hard.config",
        "com.hard.repositories",
        "com.hard.services.impl",
})
@EnableJpaRepositories("com.hard.repositories")
@EnableTransactionManagement
//@Proxy
public class AppConfig {

}
