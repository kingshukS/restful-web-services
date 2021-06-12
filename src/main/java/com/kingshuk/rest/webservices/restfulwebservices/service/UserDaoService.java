package com.kingshuk.rest.webservices.restfulwebservices.service;

import com.kingshuk.rest.webservices.restfulwebservices.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserDaoService {
    private List<User> users = new ArrayList<>();
    private static int userCount = 3;
    private static final Logger logger = LoggerFactory.getLogger(UserDaoService.class);

    @PostConstruct
    public void initialize() {
        logger.info(">>initialize()");
        users.add(new User(1,"Nehal", new Date()));
        users.add(new User(2,"Kingshuk",new Date()));
        users.add(new User(3,"Himanshu",new Date()));
        logger.info("<<initialize()");
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if(user.getId() == null)
            user.setId(++userCount);
        users.add(user);
        return user;
    }

    public User findOne(Integer id){
        return users.stream().filter(user->user.getId().equals(id)).findFirst().orElse(null);
    }

    public User deleteUserById(Integer id){
        User deletedUser = users.stream().filter(user->user.getId().equals(id)).findFirst().orElse(null);
        users.remove(deletedUser);
        return deletedUser;
    }
}
