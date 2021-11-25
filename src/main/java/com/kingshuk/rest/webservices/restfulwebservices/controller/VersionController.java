package com.kingshuk.rest.webservices.restfulwebservices.controller;

import com.kingshuk.rest.webservices.restfulwebservices.model.Name;
import com.kingshuk.rest.webservices.restfulwebservices.model.PersonV1;
import com.kingshuk.rest.webservices.restfulwebservices.model.PersonV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/person/versioning")
public class VersionController {

    // using uris
    @GetMapping("/v1/get")
    public PersonV1 personV1(){
        return new PersonV1("Kingshuk Saha");
    }

    @GetMapping("/v2/get")
    public PersonV2 personV2(){
        return new PersonV2(new Name("Kingshuk", "Saha"));
    }

    // using params
    @GetMapping(value = "/get/params", params = "ver=1")
    public PersonV1 paramsV1(){
        return new PersonV1("Kingshuk Saha");
    }

    @GetMapping(value = "/get/params", params = "ver=2")
    public PersonV2 paramsV2(){
        return new PersonV2(new Name("Kingshuk", "Saha"));
    }

    // using headers
    @GetMapping(value = "/get/headers", headers = "ver=1")
    public PersonV1 headersV1(){
        return new PersonV1("Kingshuk Saha");
    }

    @GetMapping(value = "/get/headers", headers = "ver=2")
    public PersonV2 headersV2(){
        return new PersonV2(new Name("Kingshuk", "Saha"));
    }

    // using accept headers
    @GetMapping(value = "/get/produces", produces = "application/vnd.app.ver.v1+json")
    public PersonV1 producesV1(){
        return new PersonV1("Kingshuk Saha");
    }

    @GetMapping(value = "/get/produces", produces = "application/vnd.app.ver.v2+json")
    public PersonV2 producesV2(){
        return new PersonV2(new Name("Kingshuk", "Saha"));
    }
}
