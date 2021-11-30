package com.kingshuk.rest.webservices.restfulwebservices.controller;

import com.kingshuk.rest.webservices.restfulwebservices.exception.PostNotFoundException;
import com.kingshuk.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.kingshuk.rest.webservices.restfulwebservices.model.Post;
import com.kingshuk.rest.webservices.restfulwebservices.model.User;
import com.kingshuk.rest.webservices.restfulwebservices.repository.PostRepository;
import com.kingshuk.rest.webservices.restfulwebservices.repository.UserRepository;
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
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController()
@RequestMapping("/api/jpa/users")
public class PostJpaController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}/posts")
    public List<EntityModel<Post>> getAllPosts(@PathVariable("id") Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new UserNotFoundException("id = " + id);
        List<EntityModel<Post>> entityList = new ArrayList<>();
        List<Post> posts = user.get().getPosts();
        for (Post post : posts) {
            EntityModel<Post> postEntity = EntityModel.of(post);
            Link linkToPost = linkTo(methodOn(this.getClass()).getAllPosts(id)).slash(post.getId()).withSelfRel();
            Link linkToUser = linkTo(methodOn(UserController.class).getAllUsers()).slash(user.get().getId()).withRel("user");
            postEntity.add(linkToPost);
            postEntity.add(linkToUser);
            entityList.add(postEntity);
        }
        return entityList;
    }

    @GetMapping("/{id}/posts/{postId}")
    public List<EntityModel<Post>> getPostByPostId(@PathVariable("id") Integer id, @PathVariable("postId") Integer postId) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new UserNotFoundException("id = " + id);
        Post post = user.get().getPosts().stream().filter(p -> p.getId() == postId).findFirst().orElse(null);
        if (post == null)
            throw new PostNotFoundException("postId = " + postId);
        EntityModel<Post> postEntity = EntityModel.of(post);
        Link linkToPost = linkTo(methodOn(this.getClass()).getAllPosts(id)).slash(post.getId()).withSelfRel();
        Link linkToPosts = linkTo(methodOn(this.getClass()).getAllPosts(id)).withRel("all-posts-by-user");
        Link linkToUser = linkTo(methodOn(UserController.class).getAllUsers()).slash(user.get().getId()).withRel("user");
        postEntity.add(linkToPost);
        postEntity.add(linkToPosts);
        postEntity.add(linkToUser);
        return Collections.singletonList(postEntity);
    }

    @PostMapping("/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable("id") Integer id, @Valid @RequestBody Post post) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new UserNotFoundException("id = " + id);

        post.setCreated(new Date());
        post.setUser(user.get());
        Post savedPost = postRepository.save(post);
        String location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{pathId}")
                .buildAndExpand(savedPost.getId())
                .toUriString();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.LOCATION, location);
        return new ResponseEntity<Post>(post, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/posts/{postId}")
    public Post deletePost(@PathVariable("id") Integer id, @PathVariable("postId") Integer postId) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new UserNotFoundException("id = " + id);
        Optional<Post> post = postRepository.findById(postId);
        if (post == null)
            throw new PostNotFoundException("postId = " + postId);
        postRepository.deleteById(postId);
        return post.get();
    }
}
