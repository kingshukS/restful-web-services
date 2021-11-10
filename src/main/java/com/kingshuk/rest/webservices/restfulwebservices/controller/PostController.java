package com.kingshuk.rest.webservices.restfulwebservices.controller;

import com.kingshuk.rest.webservices.restfulwebservices.exception.PostNotFoundException;
import com.kingshuk.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.kingshuk.rest.webservices.restfulwebservices.model.Post;
import com.kingshuk.rest.webservices.restfulwebservices.model.User;
import com.kingshuk.rest.webservices.restfulwebservices.service.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController()
@RequestMapping("/api/users")
public class PostController {

    @Autowired
    private UserDaoService userDaoService;

    @GetMapping("/{id}/posts")
    public List<EntityModel<Post>> getAllPosts(@PathVariable("id") Integer id) {
        User user = userDaoService.findOne(id);
        List<EntityModel<Post>> entityList = new ArrayList<>();
        List<Post> posts = user.getPosts();
        for (Post post : posts) {
            EntityModel<Post> postEntity = EntityModel.of(post);
            Link linkToPost = linkTo(methodOn(this.getClass()).getAllPosts(id)).slash(post.getId()).withSelfRel();
            Link linkToUser = linkTo(methodOn(UserController.class).getAllUsers()).slash(user.getId()).withRel("user");
            postEntity.add(linkToPost);
            postEntity.add(linkToUser);
            entityList.add(postEntity);
        }
        return entityList;
    }

    @GetMapping("/{id}/posts/{postId}")
    public List<EntityModel<Post>> getPostByPostId(@PathVariable("id") Integer id, @PathVariable("postId") Integer postId) {
        User user = userDaoService.findOne(id);
        if (user == null)
            throw new UserNotFoundException("id = " + id);
        Post post = user.getPosts().stream().filter(p -> p.getId() == postId).findFirst().orElse(null);
        if (post == null)
            throw new PostNotFoundException("postId = " + postId);
        EntityModel<Post> postEntity = EntityModel.of(post);
        Link linkToPost = linkTo(methodOn(this.getClass()).getAllPosts(id)).slash(post.getId()).withSelfRel();
        Link linkToPosts = linkTo(methodOn(this.getClass()).getAllPosts(id)).withRel("all-posts-by-user");
        Link linkToUser = linkTo(methodOn(UserController.class).getAllUsers()).slash(user.getId()).withRel("user");
        postEntity.add(linkToPost);
        postEntity.add(linkToPosts);
        postEntity.add(linkToUser);
        return Collections.singletonList(postEntity);
    }

    @PostMapping("/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable("id") Integer id, @Valid @RequestBody Post post) {
        User user = userDaoService.findOne(id);
        if (user == null)
            throw new UserNotFoundException("id = " + id);

        post.setCreated(new Date());
        Post savedPost = user.addPost(post);
        String location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{pathId}")
                .buildAndExpand(savedPost.getId())
                .toUriString();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.LOCATION, location);
        return new ResponseEntity<Post>(post, headers, HttpStatus.CREATED);
    }
}
