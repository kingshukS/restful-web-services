package com.kingshuk.rest.webservices.restfulwebservices.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "post")
public class Post {

//    @Value("${error.length.title:Default Hello World}")
//    private String defaultMessage;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Size(min = 5, message = "{error.length.title}")
    @Column(name = "title")
    private String title;
    @Size(min = 10, message = "{error.length.details}")

    @Column(name = "details")
    private String details;

    @Column(name = "created")
    private Date created;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
