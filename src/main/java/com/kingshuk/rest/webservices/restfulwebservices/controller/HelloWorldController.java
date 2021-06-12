package com.kingshuk.rest.webservices.restfulwebservices.controller;

import com.kingshuk.rest.webservices.restfulwebservices.model.HelloWorldBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/sample")
public class HelloWorldController {

    @Value("${messages.key.default:Default Hello World}")
    private String defaultMessage;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/hello-world")
    public String hellowWorld() {
        return "Hello World";
    }

    @GetMapping("/hello-world-bean")
    public HelloWorldBean hellowWorldBean() {
        return new HelloWorldBean("Hello World");
    }

    @GetMapping("/hello-world-bean/path-variable/{name}")
    public HelloWorldBean hellowWorldBeanPathVariable(@PathVariable("name") String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }

    @GetMapping("/hello-world-in")
    public String hellowWorldInternationalized(@RequestHeader(name="Accept-Language", required=false) Locale locale) {
        return messageSource.getMessage("message.hellowWorld.key", null, locale);
    }

    @GetMapping("/hello-world-in-improved")
    public String hellowWorldInternationalizedImproved() {
        return messageSource.getMessage("message.hellowWorld.key", null, LocaleContextHolder.getLocale());
    }
}
