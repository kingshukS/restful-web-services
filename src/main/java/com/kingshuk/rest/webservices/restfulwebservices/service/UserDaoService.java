package com.kingshuk.rest.webservices.restfulwebservices.service;

import com.kingshuk.rest.webservices.restfulwebservices.model.Post;
import com.kingshuk.rest.webservices.restfulwebservices.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class UserDaoService {
    private List<User> users = new ArrayList<>();
    private static int userCount = 3;
    private static final Logger logger = LoggerFactory.getLogger(UserDaoService.class);

    //@PostConstruct
    public void initialize() {
        logger.info(">>initialize()");

        Post post1 = new Post(1, "Love", "I love you Himanshu", new Date());
        Post post2 = new Post(2, "Life", "I love you Jaan", new Date());
        Post post3 = new Post(3, "World", "I love you Nehal Gupta", new Date());
        Post post4 = new Post(4, "Universe", "I love you Puchki", new Date());
        Post post5 = new Post(5, "Love and Life", "I love you Motu", new Date());

        List<Post> list1 = new ArrayList<>();
        list1.add(post1);
        list1.add(post2);

        List<Post> list2 = new ArrayList<>();
        list2.add(post3);
        list2.add(post4);


        users.add(new User(1, "Nehal", new Date(), list1));
        users.add(new User(2, "Kingshuk", new Date(), list2));
        users.add(new User(3, "Himanshu", new Date(), Collections.singletonList(post5)));

        logger.info("<<initialize()");
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if (user.getId() == null)
            user.setId(++userCount);
        users.add(user);
        return user;
    }

    public User findOne(Integer id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    public User deleteUserById(Integer id) {
        User deletedUser = users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
        users.remove(deletedUser);
        return deletedUser;
    }
}
