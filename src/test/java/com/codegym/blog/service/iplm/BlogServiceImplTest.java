package com.codegym.blog.service.iplm;

import com.codegym.blog.service.BlogService;
import com.codegym.blog.model.Blog;
import com.codegym.blog.repository.BlogRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitJupiterConfig(BlogServiceImplTestConfig.class)
class BlogServiceImplTest {

    static private Long id = 1l;
    static private String title;
    static private String content;
    static private LocalDate createDate;
    static private Blog blog;
    static private List<Blog> blogs;
    static private Page<Blog> blogsPage;
    static private Pageable pageable;

    static private List<Blog> emptyBlogs;
    static private Page<Blog> emptyBlogsPage;

    static {
        emptyBlogs = new ArrayList<>();
        emptyBlogsPage = new PageImpl<>(emptyBlogs);
    }

    static {

        title = "mvc";
        content = "mvc what is?";
        blog = new Blog(title,content,createDate);
        blog.setId(id);
        blogs = Arrays.asList(blog);
        blogsPage = new PageImpl<>(blogs);
        pageable = new PageRequest(0,20);

    }

    private BlogService blogService;

    private BlogRepository blogRepository;

    @Autowired
    public void setBlogService(BlogService blogService){
        this.blogService = blogService;
    }

    @Autowired
    public void setBlogRepository(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }


    @AfterEach
    public void resetAllMockedObject(){
        Mockito.reset(blogRepository);
    }

    //findAll of service call findAll repository
    @Test
    public void findAllWithOneBlog(){
        when(blogRepository.findAll(pageable)).thenReturn(null);
        blogService.findAll(pageable);
        verify(blogRepository).findAll(pageable);
    }

    // test return findAll of service true
    @Test
    public void findAllWithTwoBlog(){
        when(blogRepository.findAll(pageable)).thenReturn(blogsPage);
        assertEquals(blogsPage, blogService.findAll(pageable));
        verify(blogRepository).findAll(pageable);
    }

    //findAll of service return Page with Blog null
    @Test
    public void findAllWithNoBlog(){
        when(blogRepository.findAll(pageable)).thenReturn(emptyBlogsPage);
        assertEquals(emptyBlogsPage, blogService.findAll(pageable));
        verify(blogRepository).findAll(pageable);
    }
}