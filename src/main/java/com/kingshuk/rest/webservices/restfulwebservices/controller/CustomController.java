package com.kingshuk.rest.webservices.restfulwebservices.controller;

import com.kingshuk.rest.webservices.restfulwebservices.model.CustomAttribute;
import com.kingshuk.rest.webservices.restfulwebservices.model.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/custom-attributes")
public class CustomController {

    @PostMapping("/user-details")
    public UserDetails getUserDetails(@RequestBody UserDetails userDetails) {
        return userDetails;
    }

    @GetMapping("/user-details")
    public UserDetails getUserDetails() {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("myEmail");
        userDetails.setUserPoolId("myPoolId");
        userDetails.setClientId("myClientId");

        CustomAttribute customAttribute = new CustomAttribute();
        customAttribute.setCustomAdminFlag("Y");

        userDetails.setCustomAttributes(customAttribute);

        return userDetails;
    }

    @GetMapping("/user-details-list")
    public List<UserDetails> getUserDetailsList() {
        List<UserDetails> list = new ArrayList<>();

        CustomAttribute customAttribute = new CustomAttribute();
        customAttribute.setCustomAdminFlag("Y");


        UserDetails userDetails1 = new UserDetails();
        userDetails1.setEmail("myEmail");
        userDetails1.setUserPoolId("myPoolId");
        userDetails1.setClientId("myClientId");
        userDetails1.setCustomAttributes(customAttribute);

        UserDetails userDetails2 = new UserDetails();
        userDetails2.setEmail("myEmail2");
        userDetails2.setUserPoolId("myPoolId2");
        userDetails2.setClientId("myClientId2");
        userDetails2.setCustomAttributes(customAttribute);

        list.add(userDetails1);
        list.add(userDetails2);

        return list;
    }
}
