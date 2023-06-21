package com.example.cursosvirtuales.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/inicio").setViewName("home");
        
        registry.addViewController("/logeo").setViewName("index");
        registry.addViewController("/login").setViewName("index");
        registry.addViewController("/logout").setViewName("index");
        
        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/custom-error").setViewName("error");
    }
}
