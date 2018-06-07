package com.codegym.blog.controller.iplm;

import com.codegym.blog.controller.BlogController;
import com.codegym.blog.model.Blog;
import com.codegym.blog.model.Category;
import com.codegym.blog.service.BlogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringJUnitJupiterConfig(BlogControllerImplTestConfig.class)
@WebAppConfiguration
public class BlogControllerIplmTest {

    public static final String VIEW_CREATE_BLOG = "/blog/create";
    private static final String URL_CREATE_BLOG = "/create-blog";
    public static final String URL_EDIT_BLOG = "/edit-blog/{id}";
    public static final String VIEW_EDIT_BLOG = "/blog/edit";
    public static final String ERROR_404 = "error-404";

    private static Long id;
    private static String title = "mvc";
    private static String content = "mvc what is this";
    private static LocalDate createDate;
    private static Blog blog;
    private static PageRequest pageable;
    private static Category category;
    private static ArrayList<Blog> blogs;
    private static ArrayList<Blog> emptyBlogs;
    private static Page<Blog> blogsPage;
    private static Page<Blog> emptyBlogsPage;

    static {
        id = 1l;
        blog = new Blog(title,content,createDate);
        blog.setId(id);
        blogs = new ArrayList<>();
        blogs.add(blog);
        blogsPage = new PageImpl<>(blogs);
        emptyBlogs = new ArrayList<>();
        emptyBlogsPage = new PageImpl<>(emptyBlogs);
        pageable = new PageRequest(0,20);
    }

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogController blogController;

    @Autowired
    private PageableHandlerMethodArgumentResolver handlerMethodArgumentResolver;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(blogController)
                .setCustomArgumentResolvers(handlerMethodArgumentResolver)
                .build();
    }

    @Test
    void listBlogs() throws Exception {
        when(blogService.findAll(pageable)).thenReturn(blogsPage);
        mockMvc
                .perform(get("/blogs"))
                .andExpect(view().name("/blog/list"))
                .andExpect(model().attribute("blog",blogsPage));
        verify(blogService).findAll(pageable);
    }

    @Test
    void listBlogSearch() throws Exception {
        String search = "a";
        when(blogService.findAllByTitleContaining(search, pageable)).thenReturn(blogsPage);
        mockMvc
                .perform(get("/blogs")
                .param("search",search))
                .andExpect(view().name("/blog/list"))
                .andExpect(model().attribute("blog",blogsPage));
        verify(blogService).findAllByTitleContaining(search,pageable);
    }

    @Test
    void showCreateForm() throws Exception {
        mockMvc.perform(get(URL_CREATE_BLOG))
                .andExpect(view().name(VIEW_CREATE_BLOG))
                .andExpect(model().attributeExists("blog"));
    }

    @Test
    void saveBlogSuccess() throws Exception {
        mockMvc.perform(post(URL_CREATE_BLOG).param("title", title).param("content", content))
                .andExpect(view().name(VIEW_CREATE_BLOG))
                .andExpect(model().attributeExists("blog"))
                .andExpect(model().attributeExists("message"));
        verify(blogService).save(any(Blog.class));
    }

    @Test
    void showEditFormSuccess()throws Exception{
        when(blogService.findById(id)).thenReturn(blog);
        mockMvc.perform(get(URL_EDIT_BLOG,id))
                .andExpect(view().name(VIEW_EDIT_BLOG))
                .andExpect(model().attribute("blog",blog));
        verify(blogService).findById(id);
    }

    @Test
    void showEditFormNotSuccess() throws Exception {
        when(blogService.findById(id)).thenReturn(null);
        mockMvc.perform(get(URL_EDIT_BLOG, id))
                .andExpect(view().name(ERROR_404));
        verify(blogService).findById(id);
    }

    @Test
    void updateBlogSuccess(){

    }

}
