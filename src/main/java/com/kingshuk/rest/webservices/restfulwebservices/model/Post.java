package com.kingshuk.rest.webservices.restfulwebservices.model;

import javax.validation.constraints.Size;
import java.util.Date;

public class Post {

    private Integer id;
    @Size(min = 5, message = "Title must have at least 5 characters.")
    private String title;
    @Size(min = 10, message = "Post must have at least 10 characters.")
    private String details;
    private Date created;

    public Post() {
    }

    public Post(Integer id, String title, String details, Date created) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.created = created;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
