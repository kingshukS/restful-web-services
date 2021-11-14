package com.kingshuk.rest.webservices.restfulwebservices.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.kingshuk.rest.webservices.restfulwebservices.model.CustomAttribute;
import com.kingshuk.rest.webservices.restfulwebservices.model.UserDetails;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/custom-attributes")
public class CustomController {

    @PostMapping("/user-details")
    public UserDetails getUserDetails(@RequestBody UserDetails userDetails) {
        return userDetails;
    }

    @GetMapping("/user-details")
    public MappingJacksonValue getUserDetails() {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("myEmail");
        userDetails.setUserPoolId("myPoolId");
        userDetails.setClientId("myClientId");

        CustomAttribute customAttribute = new CustomAttribute();
        customAttribute.setCustomAdminFlag("Y");

        userDetails.setCustomAttributes(customAttribute);

        Set<String> fields = new HashSet<>();
        fields.add("email");
        FilterProvider filters = getFilterProvider(fields);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userDetails);
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }

    private FilterProvider getFilterProvider(Set<String> fields) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(fields);
        FilterProvider filters = new SimpleFilterProvider().addFilter("myFilter",filter);
        return filters;
    }

    @GetMapping("/user-details-list")
    public MappingJacksonValue getUserDetailsList() {
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

        Set<String> fields = new HashSet<>();
        fields.add("email");
        fields.add("clientId");
        FilterProvider filters = getFilterProvider(fields);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }
}
