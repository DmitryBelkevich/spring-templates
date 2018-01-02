package com.hard.config.init;

import com.hard.config.core.MvcConfig;
import com.hard.config.security.SecurityConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{

        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{
                MvcConfig.class,
                SecurityConfig.class,
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{
                "/",
        };
    }
}
