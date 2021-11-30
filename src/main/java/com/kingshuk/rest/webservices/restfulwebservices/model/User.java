package com.kingshuk.rest.webservices.restfulwebservices.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Transient
    private static int postCount = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Size(min = 2, message = "{error.length.name}")
    @Column(name = "name")
    private String name;

    @Past
    @Column(name = "birth_date")
    private Date birthDate;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<Post> posts;

    public User() {
    }

    public User(Integer id, String name, Date birthDate, List<Post> posts) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.posts = posts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Post addPost(Post post) {
        if (CollectionUtils.isEmpty(posts)) {
            posts = new ArrayList<>();
        }
        post.setId(++postCount);
        posts.add(post);
        return post;
    }
}
