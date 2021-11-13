package com.kingshuk.rest.webservices.restfulwebservices.controller;

import com.kingshuk.rest.webservices.restfulwebservices.model.CustomAttribute;
import com.kingshuk.rest.webservices.restfulwebservices.model.UserDetails;
import org.springframework.web.bind.annotation.*;

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
}
