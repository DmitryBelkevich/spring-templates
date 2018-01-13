package com.hard.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({
        "com.hard.config.app",
        "com.hard.dao",
        "com.hard.services.impl",
})
@EnableTransactionManagement
//@Proxy
public class AppConfig {

}
