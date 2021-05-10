package com.example.cinec_mobile_app;

public class BlogModal {
    private int id;
    private String title;
    private String description;
    private long blogDate;

    public BlogModal() {
    }

    public BlogModal(int id, String title, String description, long blogDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.blogDate = blogDate;
    }

    public BlogModal(String title, String description, long blogDate) {
        this.title = title;
        this.description = description;
        this.blogDate = blogDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getBlogDate() {
        return blogDate;
    }

    public void setBlogDate(long blogDate) {
        this.blogDate = blogDate;
    }
}
