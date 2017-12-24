package com.hard.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({
        "com.hard.services",
        "com.hard.dao",
        "com.hard.config",
})
@EnableTransactionManagement
//@Proxy
public class AppConfig {

}
