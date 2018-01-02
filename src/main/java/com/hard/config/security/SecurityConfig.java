package com.hard.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
//@ComponentScan("com.hard.config.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DBA')")
                .antMatchers("/admin").access("hasAnyRole('ROLE_ADMIN', 'ROLE_DB_ADMIN')")
                .antMatchers("/db").access("hasRole('ROLE_DB_ADMIN')")
        ;

        http.formLogin()
                .loginPage("/security/login")
//                .loginProcessingUrl("/security/auth")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/user/login?error=incorrectLoginPassword")
        ;

        http.exceptionHandling()
                .accessDeniedPage("/security/access_denied")
        ;

        http.logout()
                .logoutUrl("/logout")
        ;

        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("1234").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("1234").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("dba").password("1234").roles("DB_ADMIN");
    }
}
