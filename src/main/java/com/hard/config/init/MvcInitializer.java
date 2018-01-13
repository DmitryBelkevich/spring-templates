package com.hard.config.init;

import com.hard.config.MvcConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class MvcInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();

        applicationContext.register(MvcConfig.class);

        servletContext.addListener(new ContextLoaderListener(applicationContext));

        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("dispatcherServlet", new DispatcherServlet(applicationContext));
        servletRegistration.addMapping("/");
        servletRegistration.setLoadOnStartup(1);
    }
}
