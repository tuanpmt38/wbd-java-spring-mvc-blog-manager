package com.codegym.blog.service.iplm;

import com.codegym.blog.service.BlogService;
import com.codegym.blog.service.BlogServiceIplm;
import com.codegym.blog.repository.BlogRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BlogServiceImplTestConfig {

    @Bean
    public BlogService blogService(){
        return new BlogServiceIplm();
    }

    @Bean
    public BlogRepository blogRepository(){
        return Mockito.mock(BlogRepository.class);
    }
}