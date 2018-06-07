package com.codegym.blog.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Locale;

@Entity
@Table (name = "blogs")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    private String title;

    private String content;

    private LocalDate createDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "categories_id")
    private Category category;

    public Blog(){}

    public Blog(String title, String content, LocalDate createDate){
        this.title = title;
        this.content = content;
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return String.format("Blog[id=%d, title=%s, content = %s]", id, title, content);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }
}
