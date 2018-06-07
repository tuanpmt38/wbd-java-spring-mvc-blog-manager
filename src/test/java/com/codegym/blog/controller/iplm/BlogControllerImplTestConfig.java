package com.codegym.blog.controller.iplm;

import com.codegym.blog.service.BlogService;
import com.codegym.blog.service.BlogServiceIplm;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.codegym.blog")
@EnableSpringDataWebSupport
public class BlogControllerImplTestConfig {

    @Bean
    public BlogService  blogService(){
        return Mockito.mock(BlogServiceIplm.class);
    }

    @Bean
    public DataSource dataSource(){
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase database = builder.setType(EmbeddedDatabaseType.H2).setName("new_blog").build();
        return database;
    }
}
