package com.kingshuk.rest.webservices.restfulwebservices.controller;

import com.kingshuk.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.kingshuk.rest.webservices.restfulwebservices.model.User;
import com.kingshuk.rest.webservices.restfulwebservices.service.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;


import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private UserDaoService userDaoService;

    @GetMapping("/users")
    public List<EntityModel<User>> getAllUsers() {
        List<EntityModel<User>> entityList = new ArrayList<>();
        List<User> userList = userDaoService.findAll();
        for (User user : userList) {
            EntityModel<User> userEntity = EntityModel.of(user);
            Link linkToUser = linkTo(methodOn(this.getClass()).getAllUsers()).slash(user.getId()).withSelfRel();
            userEntity.add(linkToUser);
            entityList.add(userEntity);
        }
        return entityList;
    }

    @GetMapping("/users/{id}")
    public List<EntityModel<User>> getUser(@PathVariable("id") Integer id) {
        User user = userDaoService.findOne(id);
        if (user == null)
            throw new UserNotFoundException("id = " + id);
        EntityModel<User> userEntity = EntityModel.of(user);
        Link linkToUsers = linkTo(methodOn(this.getClass()).getAllUsers()).withRel("all-users");
        Link linkToUser = linkTo(methodOn(this.getClass()).getAllUsers()).slash(user.getId()).withSelfRel();
        userEntity.add(linkToUser);
        userEntity.add(linkToUsers);
        return Collections.singletonList(userEntity);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userDaoService.save(user);
        String location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUriString();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("location", location);
        // ResponseEntity.created(location)
        return new ResponseEntity<User>(savedUser, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{id}")
    public User deleteUser(@PathVariable("id") Integer id) {
        User deletedUser = userDaoService.deleteUserById(id);
        if (deletedUser == null)
            throw new UserNotFoundException("id = " + id);
        return deletedUser;
    }
}
